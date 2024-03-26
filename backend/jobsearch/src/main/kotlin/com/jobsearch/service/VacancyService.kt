package com.jobsearch.service

import com.jobsearch.dto.NotificationDTO
import com.jobsearch.entity.NotificationTypeEnum
import com.jobsearch.dto.JobFamilyDto
import com.jobsearch.dto.UserResponseDTO
import com.jobsearch.dto.VacancyRequestDTO
import com.jobsearch.dto.VacancyResponseDTO
import com.jobsearch.entity.Vacancy
import com.jobsearch.exception.ForbiddenException
import com.jobsearch.exception.NotFoundException
import com.jobsearch.repository.VacancyRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class VacancyService(
    val vacancyRepository: VacancyRepository,
    val jobFamilyService: JobFamilyService,
    val userService: UserService,
    val notificationService: NotificationService,
    val interestService: InterestService,
    val applicationService: ApplicationService,
) {
    fun retrieveVacancy(vacancyId: Int): VacancyResponseDTO {
        val vacancy = vacancyRepository.findById(vacancyId)
            .orElseThrow { NotFoundException("No vacancy found with id $vacancyId") }
        return mapToVacancyResponseDto(vacancy)
    }

    fun retrieveAllVacancy(): List<VacancyResponseDTO> {
        return vacancyRepository.findAll().map {
            mapToVacancyResponseDto(it)
        }
    }

    @Transactional
    fun retrieveVacancyByManager(): List<VacancyResponseDTO> {
        val manager = userService.retrieveAuthenticatedUser()
        return vacancyRepository.findByManager(manager).map {
            mapToVacancyResponseDto(it)
        }
    }

    @Transactional
    fun findVacanciesByFilter(salary: Int?, jobFamilyId: Int?, yearsOfExperience: Int?): List<VacancyResponseDTO> {
        val vacancies = vacancyRepository.findVacanciesByFilters(salary, jobFamilyId, yearsOfExperience)
        val vacanciesByCandidate = applicationService.retrieveApplicationByCandidate().map {
            it.vacancy
        }
        return vacancies.map {
            val vacancyResponseDTO = mapToVacancyResponseDto(it)
            if (vacanciesByCandidate.contains(it)) {
                vacancyResponseDTO.isApplied = true
            }
            vacancyResponseDTO
        }
    }

    @Transactional
    fun createVacancy(vacancyDto: VacancyRequestDTO): VacancyResponseDTO {
        val selectedJobFamily = jobFamilyService.findByJobFamilyId(vacancyDto.jobFamilyId)
        val managerUser = userService.retrieveAuthenticatedUser()
        if (managerUser.role?.name != "manager") throw ForbiddenException("You are not allowed to create a new vacancy.")
        val vacancyEntity = vacancyDto.let {
            Vacancy(null, it.name, it.companyName, it.salaryExpectation, it.yearsOfExperience, it.description, selectedJobFamily, managerUser)
        }

        val newVacancy = vacancyRepository.save(vacancyEntity)

        //notification about the new vacancy through email
        notificateUsers(newVacancy)



        return mapToVacancyResponseDto(newVacancy)
    }
    fun notificateUsers(newVacancy: Vacancy){
        val users = newVacancy.jobFamily.id!!.let { interestService.getUsersByJobFamilyId(it) }
        users.forEach { user ->
            val notificationDTO = NotificationDTO(
                type = NotificationTypeEnum.VACANCIES.id,
                recipient = user.id!!,
                subject = "New Vacancy Available",
                content = "A new vacancy matching your interests is available: ${newVacancy.name}",
                sender = newVacancy.manager.id!!,
                vacancy = newVacancy.id!!
            )
            notificationService.triggerNotification(notificationDTO)
        }
    }


    @Transactional
    fun updateVacancy(vacancyId: Int, vacancyDto: VacancyRequestDTO): VacancyResponseDTO {

        val vacancy = vacancyRepository.findById(vacancyId)
            .orElseThrow { NotFoundException("No vacancy found with id $vacancyId") }
        val manager = userService.retrieveAuthenticatedUser()
        if (vacancy.manager.id != manager.id ) throw ForbiddenException("You are not allowed to edit this vacancy.")

        val selectedJobFamily = jobFamilyService.findByJobFamilyId(vacancyDto.jobFamilyId)
        vacancy.name = vacancyDto.name
        vacancy.companyName = vacancyDto.companyName
        vacancy.salaryExpectation = vacancyDto.salaryExpectation
        vacancy.description = vacancyDto.description
        vacancy.jobFamily = selectedJobFamily

        val updatedVacancy = vacancyRepository.save(vacancy)
        return mapToVacancyResponseDto(updatedVacancy)
    }

    @Transactional
    fun deleteVacancy(vacancyId: Int) {
        val vacancy = vacancyRepository.findById(vacancyId)
            .getOrElse { throw NotFoundException("Vacancy not found") }
        val manager = userService.retrieveAuthenticatedUser()
        if (vacancy.manager != manager ) throw ForbiddenException("You are not allowed to erase this vacancy.")
        vacancyRepository.delete(vacancy)
    }

    /**
     * Maps a Vacancy object to a VacancyResponseDTO object.
     *
     * @param vacancy the Vacancy object to map
     * @return the mapped VacancyResponseDTO object
     */
    private fun mapToVacancyResponseDto(vacancy: Vacancy): VacancyResponseDTO {
        return vacancy.let {
            VacancyResponseDTO(
                it.id!!,
                it.name,
                it.companyName,
                it.salaryExpectation,
                it.yearsOfExperience,
                it.description,
                it.jobFamily.let{ jobFamily -> JobFamilyDto(jobFamily.id, jobFamily.name) },
                it.manager.let { user ->
                    UserResponseDTO(
                        user.id!!,
                        user.firstName,
                        user.lastName,
                        user.email,
                        user.role?.id!!
                    )
                }
            )
        }
    }
}