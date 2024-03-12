package com.jobsearch.service

import com.jobsearch.dto.NotificationDTO
import com.jobsearch.dto.VacancyDto
import com.jobsearch.entity.Vacancy
import com.jobsearch.exception.NotFoundException
import com.jobsearch.repository.VacancyRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class VacancyService(
    val vacancyRepository: VacancyRepository,
    val jobFamilyService: JobFamilyService,
    val userService: UserService,
    val notificationService: NotificationService,
    val interestService: InterestService
) {
    fun createVacancy(vacancyDto: VacancyDto): VacancyDto {
        val selectedJobFamily = jobFamilyService.findByJobFamilyId(vacancyDto.jobFamilyId!!)
            .orElseThrow { NotFoundException("No vacancy found with id ${vacancyDto.jobFamilyId}") }

        val managerUser = userService.retrieveAuthenticatedUser()

        val vacancyEntity = vacancyDto.let {
            Vacancy(it.id, it.name, it.companyName, it.salaryExpectation, it.yearsOfExperience, it.description, selectedJobFamily, managerUser)
        }
        val newVacancy = vacancyRepository.save(vacancyEntity)

        //notification about the new vacancy through email
        notificateUser(newVacancy)


        return mapToVacancyDto(newVacancy)
    }
    fun notificateUser(newVacancy: Vacancy){

        val users = newVacancy.jobFamily.id!!.let { interestService.getUsersByJobFamilyId(it) }
        users.forEach { user ->
            val notificationDTO = NotificationDTO(
                type = 1,
                recipient = user.id!!,
                subject = "New Vacancy Available",
                content = "A new vacancy matching your interests is available: ${newVacancy.name}",
                sender = null,
                vacancy = newVacancy.id
            )

            notificationService.triggerNotification(notificationDTO)
        }
    }
    fun retrieveVacancy(vacancyId: Int): VacancyDto {
        val vacancy = vacancyRepository.findById(vacancyId)
            .orElseThrow { NotFoundException("No vacancy found with id $vacancyId") }

        return mapToVacancyDto(vacancy)
    }

    fun retrieveAllVacancy(): List<VacancyDto> {
        val persons = vacancyRepository.findAll()

        return persons.map {
            mapToVacancyDto(it)
        }
    }

    fun updateVacancy(vacancyId: Int, vacancyDto: VacancyDto): VacancyDto {
        val vacancy = vacancyRepository.findById(vacancyId)
            .orElseThrow { NotFoundException("No vacancy found with id $vacancyId") }

        val selectedJobFamily = jobFamilyService.findByJobFamilyId(vacancyDto.jobFamilyId!!)
            .orElseThrow { NotFoundException("No vacancy found with id ${vacancyDto.jobFamilyId}") }

        vacancy.name = vacancyDto.name
        vacancy.companyName = vacancyDto.companyName
        vacancy.salaryExpectation = vacancyDto.salaryExpectation
        vacancy.description = vacancyDto.description
        vacancy.jobFamily = selectedJobFamily

        val updatedVacancy = vacancyRepository.save(vacancy)
        return mapToVacancyDto(updatedVacancy)
    }

    fun deleteVacancy(vacancyId: Int): String {
        val vacancy = vacancyRepository.findById(vacancyId)
            .orElseThrow { NotFoundException("No vacancy found with id $vacancyId") }

        vacancyRepository.delete(vacancy)
        return "Vacancy deleted successfully"
    }

    @Transactional
    fun findVacanciesByFilter(salary: Int?, jobFamilyId: Int?, yearsOfExperience: Int?): List<VacancyDto> {
        val vacancies = vacancyRepository.findVacanciesByFilters(salary, jobFamilyId, yearsOfExperience)
        return vacancies.map {
            mapToVacancyDto(it)
        }
    }

    fun mapToVacancyDto(vacancy: Vacancy): VacancyDto {
        return vacancy.let {
            VacancyDto(
                it.id,
                it.name,
                it.companyName,
                it.salaryExpectation,
                it.yearsOfExperience,
                it.description,
                it.jobFamily.id,
                it.jobFamily.name,
                it.manager.id
            )
        }
    }

}