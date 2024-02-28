package com.jobsearch.service

import com.jobsearch.dto.ApplicationDTO
import com.jobsearch.entity.Application
import com.jobsearch.repository.ApplicationRepository
import com.jobsearch.repository.CvRepository
import com.jobsearch.repository.UserRepository
import com.jobsearch.repository.VacancyRepository
import org.springframework.stereotype.Service

@Service
class ApplicationService(
    private val applicationRepository: ApplicationRepository,
    private val userRepository: UserRepository,
    private val cvRepository: CvRepository,
    private val vacancyRepository: VacancyRepository
) {


    fun createApplication(applicationDTO: ApplicationDTO): ApplicationDTO {

        val candidate = userRepository.findById(applicationDTO.userId.toLong())
            .orElseThrow { NoSuchElementException("User not found with ID: ${applicationDTO.userId}") }

        val cv = cvRepository.findById(applicationDTO.cvId)
            .orElseThrow { NoSuchElementException("Cv not found with ID: ${applicationDTO.cvId}") }

        val vacancy = vacancyRepository.findById(applicationDTO.vacancyId)
            .orElseThrow { NoSuchElementException("Vacancy not found with ID: ${applicationDTO.vacancyId}") }

        val applicationEntity = Application(
            candidate = candidate,
            cv = cv,
            vacancy = vacancy,
            applicationStatus = applicationDTO.applicationStatus
        )

        val newApplication = applicationEntity.let { applicationRepository.save(it) }

        return ApplicationDTO(
            newApplication.applicationId,
            newApplication.candidate.id!!,
            newApplication.cv.id!!,
            newApplication.vacancy.id!!,
            newApplication.applicationStatus
        )

    }


    fun retrieveApplication(applicationId: Int): ApplicationDTO {
        val application = applicationRepository.findById(applicationId)
            .orElseThrow { NoSuchElementException("No application found with $applicationId") }

        return ApplicationDTO(
            applicationId = application.applicationId,
            userId = application.candidate.id!!,
            cvId = application.cv.id!!,
            vacancyId = application.vacancy.id!!,
            applicationStatus = application.applicationStatus
        )

    }


    fun retrieveAllApplication(): List<ApplicationDTO> {
        val application = applicationRepository.findAll()

        return application.map {
            ApplicationDTO(
                it.applicationId!!,
                it.candidate.id!!,
                it.cv.id!!,
                it.vacancy.id!!,
                it.applicationStatus)
        }
    }


    fun updateApplication(applicationId: Int, applicationDTO: ApplicationDTO) : ApplicationDTO{
        val application = applicationRepository.findById(applicationId)
            .orElseThrow{NoSuchElementException("No application founded with id $applicationId")}

        applicationDTO.apply {
            application.applicationStatus = this.applicationStatus
        }

        val updatedApplication = applicationRepository.save(application)

        return ApplicationDTO(
            applicationId = updatedApplication.applicationId,
            userId = updatedApplication.candidate.id!!,
            cvId = updatedApplication.cv.id!!,
            vacancyId = updatedApplication.vacancy.id!!,
            applicationStatus = updatedApplication.applicationStatus
        )
    }


    fun deleteApplication(applicationId: Int) : String{
        val application = applicationRepository.findById(applicationId)
            .orElseThrow{NoSuchElementException("No application founded with id $applicationId")}

        applicationRepository.delete(application)

        return "Application Deleted Successfully"

    }

}