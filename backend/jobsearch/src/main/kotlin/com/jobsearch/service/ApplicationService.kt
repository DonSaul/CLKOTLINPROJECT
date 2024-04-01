package com.jobsearch.service

import com.jobsearch.dto.ApplicationDTO
import com.jobsearch.entity.Application
import com.jobsearch.entity.ApplicationStatus
import com.jobsearch.entity.Vacancy
import com.jobsearch.exception.ForbiddenException
import com.jobsearch.exception.NotFoundException
import com.jobsearch.repository.*
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ApplicationService(
    private val applicationRepository: ApplicationRepository,
    private val userRepository: UserRepository,
    private val cvRepository: CvRepository,
    private val vacancyRepository: VacancyRepository,
    private val userService: UserService,
    private val statusService: StatusService,
    private val statusRepository: StatusRepository,

) {

    @Transactional
    fun createApplication(applicationDTO: ApplicationDTO): ApplicationDTO {

        val candidate = userService.retrieveAuthenticatedUser()

        val defaultStatus = statusRepository.findById(ApplicationStatus.APPLIED.id).get()

        val cv = cvRepository.findFirstByUserOrderByIdDesc(candidate)
            .orElseThrow { NotFoundException("No CV found for this user") }

        val vacancy = vacancyRepository.findById(applicationDTO.vacancyId)
            .orElseThrow { NotFoundException("Vacancy not found with ID: ${applicationDTO.vacancyId}") }

        val applicationEntity = Application(
            candidate = candidate,
            cv = cv,
            vacancy = vacancy,
            applicationStatus = defaultStatus
        )
        if (candidateAlreadyApplied(applicationEntity)){
            throw ForbiddenException("Candidate have already applied to this vacancy")
        } else {
            val newApplication = applicationEntity.let { applicationRepository.save(it) }

            return ApplicationDTO(
                newApplication.id,
                newApplication.candidate.id!!,
                newApplication.cv.id!!,
                newApplication.vacancy.id!!,
                newApplication.applicationStatus.name,
                newApplication.applicationStatus.id
            )
        }



    }


    fun retrieveApplication(applicationId: Int): ApplicationDTO {
        val application = applicationRepository.findById(applicationId)
            .orElseThrow { NotFoundException("No application found with $applicationId") }


        return mapToApplicationDTO(application)

    }


    fun retrieveAllApplication(): List<ApplicationDTO> {
        val application = applicationRepository.findAll()

        return application.map {
            mapToApplicationDTO(it)
        }
    }

    @Transactional
    fun updateApplicationStatus( applicationDTO: ApplicationDTO) : ApplicationDTO{
        val application = applicationRepository.findById(applicationDTO.applicationId!!)
            .orElseThrow{NotFoundException("No application founded with id ${applicationDTO.applicationId}")}


        application.apply {
            application.applicationStatus = statusRepository.findById(applicationDTO.applicationStatusId!!).get()
        }

        val updatedApplication = applicationRepository.save(application)

        return mapToApplicationDTO(updatedApplication)
    }

    @Transactional
    fun deleteApplication(applicationId: Int) : String{
        val application = applicationRepository.findById(applicationId)
            .orElseThrow{NotFoundException("No application founded with id $applicationId")}

        applicationRepository.delete(application)

        return "Application Deleted Successfully"

    }

    fun retrieveApplicationByAuthenticatedCandidate(): List<Application> {
        val candidate = userService.retrieveAuthenticatedUser()
        return applicationRepository.findByCandidate(candidate)
    }
    fun retrieveApplicationByVacancy(vacancy: Vacancy): List<Application> {
        val vacancyApplicationList = applicationRepository.findByVacancy(vacancy)
        return vacancyApplicationList
    }

    fun mapToApplicationDTO(application: Application): ApplicationDTO {
        return application.let {
            ApplicationDTO(
                    it.id,
                    it.candidate.id!!,
                    it.cv.id!!,
                    it.vacancy.id!!,
                    it.applicationStatus.name,
                    it.applicationStatus.id
            )
        }
    }

    private fun candidateAlreadyApplied(applicationEntity: Application): Boolean {
        val application =
            applicationRepository.findFirstByCandidateAndVacancy(applicationEntity.candidate, applicationEntity.vacancy)
        return application != null
    }
}