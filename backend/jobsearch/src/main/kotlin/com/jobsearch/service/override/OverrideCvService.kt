package com.jobsearch.service.override

import com.jobsearch.dto.*
import com.jobsearch.dto.override.OverrideCvRequestDTO
import com.jobsearch.entity.Cv
import com.jobsearch.entity.Job
import com.jobsearch.entity.Project
import com.jobsearch.exception.NotFoundException
import com.jobsearch.repository.CvRepository
import com.jobsearch.repository.JobFamilyRepository
import com.jobsearch.repository.SkillRepository
import com.jobsearch.service.InterestService
import com.jobsearch.service.UserService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class OverrideService(
    private val cvRepository: CvRepository,
    private val skillRepository: SkillRepository,
    private val jobFamilyRepository: JobFamilyRepository,
    private val userService: UserService,
    private val interestService: InterestService,) {

    @Transactional
    fun createCvOverride(cvDTO: OverrideCvRequestDTO): CvResponseDTO {

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
                user = it.user
            )
        }

        // Adding jobs to CV
        cvDTO.jobs.forEach { jobDTO ->

            cv.jobs?.add(
                Job(
                    cv = cv,
                    startDate = jobDTO.startDate,
                    endDate = jobDTO.endDate,
                    position = jobDTO.position,
                    description = jobDTO.description,
                    jobFamily = jobFamilyRepository.findById(jobDTO.jobFamilyId)
                        .orElseThrow { NotFoundException("No Job Family found with id ${jobDTO.jobFamilyId}") }
                )
            )
        }

        // Adding projects to CV
        cvDTO.projects.forEach { projectDTO ->

            cv.projects?.add(
                Project(
                    cv = cv,
                    name = projectDTO.name,
                    description = projectDTO.description,
                    jobFamily = jobFamilyRepository.findById(projectDTO.jobFamilyId)
                        .orElseThrow { NotFoundException("No Job Family found with id ${projectDTO.jobFamilyId}") }
                )
            )
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

        return mapToCvDTO(newCv)
    }

    private fun mapToCvDTO(cv: Cv): CvResponseDTO {
        return CvResponseDTO(
            id = cv.id!!,
            yearsOfExperience = cv.yearsOfExperience,
            salaryExpectation = cv.salaryExpectation,
            education = cv.education,
            jobs = cv.jobs?.map { job ->
                JobResponseDTO(
                    id = job.id!!,
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
                    id = project.id!!,
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
