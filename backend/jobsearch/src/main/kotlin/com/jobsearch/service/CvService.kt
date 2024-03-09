package com.jobsearch.service

import com.jobsearch.dto.*
import com.jobsearch.entity.Cv
import com.jobsearch.entity.Job
import com.jobsearch.entity.Project
import com.jobsearch.exception.NotFoundException
import com.jobsearch.repository.CvRepository
import com.jobsearch.repository.JobFamilyRepository
import com.jobsearch.repository.SkillRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CvService(
    private val cvRepository: CvRepository,
    private val skillRepository: SkillRepository,
    private val jobFamilyRepository: JobFamilyRepository,
    private val userService: UserService) {

    @Transactional
    fun createCv(cvDTO: CvRequestDTO): CvResponseDTO {

        val cv = cvDTO.let {
            Cv(
                id = null,
                yearsOfExperience = it.yearsOfExperience,
                salaryExpectation = it.salaryExpectation,
                education = it.education,
                jobs = mutableSetOf(),
                projects = mutableSetOf(),
                skills = mutableSetOf(),
                user = userService.retrieveAuthenticatedUser()
            )
        }

        // Adding jobs to CV
        cvDTO.jobs.forEach { jobDTO ->
            val jobFamily = jobFamilyRepository.findById(jobDTO.jobFamilyId)
                .orElseThrow { NotFoundException("No Job Family found with id ${jobDTO.jobFamilyId}") }

            cv.jobs?.add(
                Job(
                    cv = cv,
                    startDate = jobDTO.startDate,
                    endDate = jobDTO.endDate,
                    position = jobDTO.position,
                    description = jobDTO.description,
                    jobFamily = jobFamily
                )
            )
        }

        // Adding projects to CV
        cvDTO.projects.forEach { projectDTO ->
            val jobFamily = jobFamilyRepository.findById(projectDTO.jobFamilyId)
                .orElseThrow { NotFoundException("No Job Family found with id ${projectDTO.jobFamilyId}") }

            jobFamily?.let {
                cv.projects?.add(
                    Project(
                        cv = cv,
                        name = projectDTO.name,
                        description = projectDTO.description,
                        jobFamily = it
                    )
                )
            }
        }

        val skillIds = cvDTO.longSkillString.split(",").map { it.toInt() }.toSet()

        // Adding skills to CV
        skillIds.forEach { skillId ->
            val skill = skillRepository.findById(skillId).orElse(null)
            skill?.let {
                cv.skills?.add(it)
            }
        }

        val newCv = cvRepository.save(cv)

        return mapToCvDTO(newCv)
    }


    fun retrieveCv(cvId: Int): CvResponseDTO {
        val cv = cvRepository.findById(cvId)
            .orElseThrow { NotFoundException("No CV found with id $cvId") }

        return mapToCvDTO(cv)
    }

    fun retrieveAllCvs(): List<CvResponseDTO> {
        val cvs = cvRepository.findAll()

        return cvs.map { mapToCvDTO(it) }
    }

    @Transactional
    fun updateCv(cvId: Int, cvDTO: CvRequestDTO): CvResponseDTO {
        val cv = cvRepository.findById(cvId)
            .orElseThrow { NotFoundException("No CV found with id $cvId") }

        val authenticatedUser = userService.retrieveAuthenticatedUser()

        if (cv.user != authenticatedUser) {
            throw IllegalAccessException("You are not authorized to edit this CV")
        }

        // Updating attributes
        cv.apply {
            yearsOfExperience = cvDTO.yearsOfExperience
            salaryExpectation = cvDTO.salaryExpectation
            education = cvDTO.education
        }

        // Updating jobs

        // Removing jobs from the CV that are not in the request
        cv.jobs?.removeIf { job -> !cvDTO.jobs.any { it.jobId == job.jobId } }

        cvDTO.jobs.forEach { dto ->
            val existingJob = cv.jobs?.find { it.jobId == dto.jobId }

            if (existingJob != null) {
                // If job exists, the properties are updated
                existingJob.apply {
                    startDate = dto.startDate
                    endDate = dto.endDate
                    position = dto.position
                    description = dto.description
                    jobFamily = jobFamilyRepository.findById(dto.jobFamilyId)
                        .orElseThrow { NotFoundException("No Job Family found with id ${dto.jobFamilyId}") }
                }
            } else {
                // If job doesn't exist, a new one is created and added to the CV
                val newJob = Job(
                    cv = cv,
                    startDate = dto.startDate,
                    endDate = dto.endDate,
                    position = dto.position,
                    description = dto.description,
                    jobFamily = jobFamilyRepository.findById(dto.jobFamilyId)
                        .orElseThrow { NotFoundException("No Job Family found with id ${dto.jobFamilyId}") }
                )
                cv.jobs?.add(newJob)
            }
        }

        // Updating projects

        // Removing projects from the CV that are not in the request
        cv.projects?.removeIf { project -> !cvDTO.projects.any { it.projectId == project.projectId } }

        cvDTO.projects.forEach { dto ->
            val existingProject = cv.projects?.find { it.projectId == dto.projectId }

            if (existingProject != null) {
                // If project exists, the properties are updated
                existingProject.apply {
                    name = dto.name
                    description = dto.description
                    jobFamily = jobFamilyRepository.findById(dto.jobFamilyId)
                        .orElseThrow { NotFoundException("No JobFamily found with id ${dto.jobFamilyId}") }
                }
            } else {
                // If project doesn't exist, a new one is created and added to the CV
                val newProject = Project(
                    cv = cv,
                    name = dto.name,
                    description = dto.description,
                    jobFamily = jobFamilyRepository.findById(dto.jobFamilyId)
                        .orElseThrow { NotFoundException("No JobFamily found with id ${dto.jobFamilyId}") }
                )
                cv.projects?.add(newProject)
            }
        }

        // Updating skills
        val skillIds = cvDTO.longSkillString.split(",").map { it.toInt() }.toSet()

        // Getting current skills from the CV
        val currentSkillIds = cv.skills?.map { it.skillId!! }?.toSet() ?: emptySet()

        // Removing skills from the CV that are not in the request
        cv.skills?.removeIf { skill -> skill.skillId !in skillIds }

        // Adding new skills to the CV
        skillIds.filterNot { it in currentSkillIds }.forEach { id ->
            skillRepository.findById(id).ifPresent { cv.skills?.add(it) }
        }

        val updatedCv = cvRepository.save(cv)

        return mapToCvDTO(updatedCv)
    }


    fun deleteCv(cvId: Int): String {
        val cv = cvRepository.findById(cvId)
            .orElseThrow { NotFoundException("No CV found with id $cvId") }

        cvRepository.delete(cv)

        return "Cv deleted successfully"
    }

    fun retrieveAllMyAccountsCvs(): List<CvResponseDTO> {
        val cvs = cvRepository.findByUser(userService.retrieveAuthenticatedUser())

        return cvs.map { mapToCvDTO(it) }
    }

    fun retrieveMyAccountsCv(cvId: Int): CvResponseDTO {
        val cv = cvRepository.findByUserAndId(userService.retrieveAuthenticatedUser(), cvId)

        return mapToCvDTO(cv)
    }

    fun retrieveMyAccountsLastCv(): CvResponseDTO {
        val cv = cvRepository.findFirstByUserOrderByIdDesc(userService.retrieveAuthenticatedUser())

        return mapToCvDTO(cv)
    }

    private fun mapToCvDTO(cv: Cv): CvResponseDTO {
        return CvResponseDTO(
            id = cv.id!!,
            yearsOfExperience = cv.yearsOfExperience,
            salaryExpectation = cv.salaryExpectation,
            education = cv.education,
            jobs = cv.jobs?.map { job ->
                JobResponseDTO(
                    jobId = job.jobId!!,
                    startDate = job.startDate,
                    endDate = job.endDate,
                    position = job.position,
                    description = job.description,
                    jobFamily = JobFamilyDto(
                        id = job.jobFamily.id!!,
                        name = job.jobFamily.name
                    )
                )
            }?.toSet() ?: emptySet(),
            projects = cv.projects?.map { project ->
                ProjectResponseDTO(
                    projectId = project.projectId!!,
                    name = project.name,
                    description = project.description,
                    jobFamily = JobFamilyDto(
                        id = project.jobFamily.id!!,
                        name = project.jobFamily.name
                    )
                )
            }?.toSet() ?: emptySet(),
            skills = cv.skills?.map { skill ->
                SkillDTO(
                    skillId = skill.skillId!!,
                    name = skill.name
                )
            }?.toSet() ?: emptySet()
        )
    }

}
