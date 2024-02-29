package com.jobsearch.service

import com.jobsearch.dto.ApplicationDTO
import com.jobsearch.entity.Application
import com.jobsearch.repository.*
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


    fun createApplication(applicationDTO: ApplicationDTO): ApplicationDTO {

        val candidate = userService.retrieveAuthenticatedUser()

        val defaultStatus= statusService.retrieveStatus(2)



        val cv = cvRepository.findById(applicationDTO.cvId!!)
            .orElseThrow { NoSuchElementException("Cv not found with ID: ${applicationDTO.cvId}") }

        val vacancy = vacancyRepository.findById(applicationDTO.vacancyId)
            .orElseThrow { NoSuchElementException("Vacancy not found with ID: ${applicationDTO.vacancyId}") }

        val applicationEntity = Application(
            candidate = candidate,
            cv = cv,
            vacancy = vacancy,
            applicationStatus = statusRepository.findById(2).get()
        )

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


    fun retrieveApplication(applicationId: Int): ApplicationDTO {
        val application = applicationRepository.findById(applicationId)
            .orElseThrow { NoSuchElementException("No application found with $applicationId") }


        return mapToApplicationDTO(application)

    }


    fun retrieveAllApplication(): List<ApplicationDTO> {
        val application = applicationRepository.findAll()

        return application.map {
            mapToApplicationDTO(it)
        }
    }


    fun updateApplicationStatus( applicationDTO: ApplicationDTO) : ApplicationDTO{
        val application = applicationRepository.findById(applicationDTO.applicationId!!)
            .orElseThrow{NoSuchElementException("No application founded with id ${applicationDTO.applicationId}")}


        application.apply {
            application.applicationStatus = statusRepository.findById(applicationDTO.applicationStatusId!!).get()
        }

        val updatedApplication = applicationRepository.save(application)

        return mapToApplicationDTO(updatedApplication)
    }


    fun deleteApplication(applicationId: Int) : String{
        val application = applicationRepository.findById(applicationId)
            .orElseThrow{NoSuchElementException("No application founded with id $applicationId")}

        applicationRepository.delete(application)

        return "Application Deleted Successfully"

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

}