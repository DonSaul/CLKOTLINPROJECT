package com.jobsearch.service

import com.jobsearch.dto.CvRequestDTO
import com.jobsearch.dto.CvResponseDTO
import com.jobsearch.entity.Cv
import com.jobsearch.entity.Job
import com.jobsearch.entity.Project
import com.jobsearch.exception.NotFoundException
import com.jobsearch.mapper.CvMapper
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
    private val userService: UserService,
    private val interestService: InterestService,
    private val cvMapper: CvMapper
) {

    @Transactional
    fun createCv(cvDTO: CvRequestDTO): CvResponseDTO {

        // Setting basic attributes
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
            val jobFamily = jobFamilyRepository.findById(jobDTO.jobFamilyId).orElse(null)
            interestService.createInterest(jobFamily.id!!, cv.user.id!!)
            jobFamily?.let {
                cv.jobs?.add(
                    Job(
                        cv = cv,
                        startDate = jobDTO.startDate,
                        endDate = jobDTO.endDate,
                        company = jobDTO.company,
                        position = jobDTO.position,
                        description = jobDTO.description,
                        jobFamily = it
                    )
                )
            }
        }

        // Adding projects to CV
        cvDTO.projects.forEach { projectDTO ->
            val jobFamily = jobFamilyRepository.findById(projectDTO.jobFamilyId).orElse(null)

            interestService.createInterest(jobFamily.id!!, cv.user.id!!)
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

        // Adding skills to CV
        cvDTO.skillIds.forEach { skillId ->
            val skill = skillRepository.findById(skillId)
                .orElseThrow { NotFoundException("No Skill found with id $skillId") }

            skill?.let {
                cv.skills?.add(it)
            }
        }

        val newCv = cvRepository.save(cv)

        return cvMapper.mapToDto(newCv)
    }


    fun retrieveCv(cvId: Int): CvResponseDTO {
        val cv = cvRepository.findById(cvId)
            .orElseThrow { NotFoundException("No CV found with id $cvId") }

        return cvMapper.mapToDto(cv)
    }

    fun retrieveAllCvs(): List<CvResponseDTO> {
        val cvs = cvRepository.findAll()

        return cvs.map { cvMapper.mapToDto(it) }
    }

    @Transactional
    fun updateCv(cvId: Int, cvDTO: CvRequestDTO): CvResponseDTO {
        val cv = cvRepository.findById(cvId)
            .orElseThrow { NotFoundException("No CV found with id $cvId") }

        val authenticatedUser = userService.retrieveAuthenticatedUser()

        // Checking if user is the owner of the CV he's trying to edit
        if (cv.user != authenticatedUser) {
            throw IllegalAccessException("You are not authorized to edit this CV")
        }

        // Updating basic attributes
        cv.apply {
            yearsOfExperience = cvDTO.yearsOfExperience
            salaryExpectation = cvDTO.salaryExpectation
            education = cvDTO.education
        }

        // Updating jobs
        // Removing jobs from the CV that are not in the request
        cv.jobs?.removeIf { job ->
            val isJobInDTO = cvDTO.jobs.any { it.id == job.id }
            if (!isJobInDTO) {
                interestService.deleteInterestByUserIdAndJobFamilyId(job.jobFamily.id!!, cv.user.id!!)
            }
            !isJobInDTO
        }

        cvDTO.jobs.forEach { dto ->
            val existingJob = cv.jobs?.find { it.id == dto.id }

            if (existingJob != null) {
                // If job exists, the properties are updated
                existingJob.apply {
                    startDate = dto.startDate
                    endDate = dto.endDate
                    position = dto.position
                    description = dto.description
                    jobFamily = jobFamilyRepository.findById(dto.jobFamilyId)
                        .orElseThrow { NotFoundException("No Job Family found with id ${dto.jobFamilyId}") }
                    interestService.updateInterest(dto.jobFamilyId, cv.user.id!!)
                }
            } else {
                // If job doesn't exist, a new one is created and added to the CV
                val newJob = Job(
                    cv = cv,
                    startDate = dto.startDate,
                    endDate = dto.endDate,
                    position = dto.position,
                    company = dto.company,
                    description = dto.description,
                    jobFamily = jobFamilyRepository.findById(dto.jobFamilyId)
                        .orElseThrow { NotFoundException("No Job Family found with id ${dto.jobFamilyId}") }
                )
                interestService.createInterest(newJob.jobFamily.id!!, cv.user.id!!)
                cv.jobs?.add(newJob)
            }
        }

        // Updating projects
        // Removing projects from the CV that are not in the request
        cv.projects?.removeIf { project ->
            val isProjectInDTO = cvDTO.projects.any { it.id == project.id }
            if (!isProjectInDTO) {
                interestService.deleteInterestByUserIdAndJobFamilyId(project.jobFamily.id!!, cv.user.id!!)
            }
            !isProjectInDTO
        }

        cvDTO.projects.forEach { dto ->
            val existingProject = cv.projects?.find { it.id == dto.id }

            if (existingProject != null) {
                // If project exists, the properties are updated
                existingProject.apply {
                    name = dto.name
                    description = dto.description
                    jobFamily = jobFamilyRepository.findById(dto.jobFamilyId)
                        .orElseThrow { NotFoundException("No JobFamily found with id ${dto.jobFamilyId}") }
                    interestService.updateInterest(dto.jobFamilyId, cv.user.id!!)
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
                interestService.createInterest(newProject.jobFamily.id!!, cv.user.id!!)
                cv.projects?.add(newProject)
            }
        }

        // Updating skills
        // Getting current skills from the CV
        val currentSkillIds = cv.skills?.map { it.skillId!! }?.toSet() ?: emptySet()

        // Removing skills from the CV that are not in the request
        cv.skills?.removeIf { skill -> skill.skillId !in cvDTO.skillIds }

        // Adding new skills to the CV
        cvDTO.skillIds.filterNot { it in currentSkillIds }.forEach { skillId ->
            val skill = skillRepository.findById(skillId)
                .orElseThrow { NotFoundException("No Skill found with id $skillId") }

            skill?.let {
                cv.skills?.add(it)
            }
        }

        val updatedCv = cvRepository.save(cv)

        return cvMapper.mapToDto(updatedCv)
    }
    fun deleteCv(cvId: Int): String {
        val cv = cvRepository.findById(cvId)
            .orElseThrow { NotFoundException("No CV found with id $cvId") }
        cv.projects?.forEach { project -> interestService.deleteInterestByUserIdAndJobFamilyId(project.jobFamily.id!!, cv.user.id!!) }
        cvRepository.delete(cv)

        return "CV deleted successfully"
    }

    fun retrieveAllMyAccountsCvs(): List<CvResponseDTO> {
        val cvs = cvRepository.findByUser(userService.retrieveAuthenticatedUser())

        return cvs.map { cvMapper.mapToDto(it) }
    }

    fun retrieveMyAccountsCv(cvId: Int): CvResponseDTO {
        val cv = cvRepository.findByUserAndId(userService.retrieveAuthenticatedUser(), cvId)

        return cvMapper.mapToDto(cv)
    }

    fun retrieveMyAccountsLastCv(): CvResponseDTO {
        val cv = cvRepository.findFirstByUserOrderByIdDesc(userService.retrieveAuthenticatedUser())

        return cvMapper.mapToDto(cv!!)
    }
}
