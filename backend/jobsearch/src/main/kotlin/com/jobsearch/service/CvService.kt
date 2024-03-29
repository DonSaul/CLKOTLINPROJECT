package com.jobsearch.service

import com.jobsearch.dto.*
import com.jobsearch.entity.Cv
import com.jobsearch.entity.Project
import com.jobsearch.repository.CvRepository
import com.jobsearch.repository.JobFamilyRepository
import com.jobsearch.repository.SkillRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CvService(
    private val cvRepository: CvRepository,
    private val skillRepository: SkillRepository,
    private val jobFamilyRepository: JobFamilyRepository) {

    @Transactional
    fun createCv(cvDTO: CvRequestDTO): CvResponseDTO {

        val cv = cvDTO.let {
            Cv(
                id = null,
                yearsOfExperience = it.yearsOfExperience,
                salaryExpectation = it.salaryExpectation,
                education = it.education,
                projects = mutableSetOf(),
                skills = mutableSetOf()
            )
        }

        // Adding projects to CV
        cvDTO.projects.forEach { projectDTO ->
            val jobFamily = jobFamilyRepository.findById(projectDTO.jobFamilyId).orElse(null)

            jobFamily?.let {
                cv.projects?.add(
                    Project(
                        name = projectDTO.name,
                        description = projectDTO.description,
                        cv = cv,
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

        return mapToCvResponseDTO(newCv)
    }


    fun retrieveCv(cvId: Int): CvResponseDTO {
        val cv = cvRepository.findById(cvId)
            .orElseThrow { NoSuchElementException("No CV found with id $cvId") }

        return mapToCvResponseDTO(cv)
    }

    fun retrieveAllCvs(): List<CvResponseDTO> {
        val cvs = cvRepository.findAll()

        return cvs.map { mapToCvResponseDTO(it) }
    }

    @Transactional
    fun updateCv(cvId: Int, cvDTO: CvRequestDTO): CvResponseDTO {
        val cv = cvRepository.findById(cvId)
            .orElseThrow { NoSuchElementException("No CV found with id $cvId") }

        cv.yearsOfExperience = cvDTO.yearsOfExperience
        cv.salaryExpectation = cvDTO.salaryExpectation
        cv.education = cvDTO.education

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
                        .orElseThrow { NoSuchElementException("No JobFamily found with id ${dto.jobFamilyId}") }
                }
            } else {
                // If project doesn't exist, a new one is created and added to the CV
                val newProject = Project(
                    cv = cv,
                    name = dto.name,
                    description = dto.description,
                    jobFamily = jobFamilyRepository.findById(dto.jobFamilyId)
                        .orElseThrow { NoSuchElementException("No JobFamily found with id ${dto.jobFamilyId}") }
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

        return mapToCvResponseDTO(updatedCv)
    }


    fun deleteCv(cvId: Int): String {
        val cv = cvRepository.findById(cvId)
            .orElseThrow { NoSuchElementException("No CV found with id $cvId") }

        cvRepository.delete(cv)

        return "Cv deleted successfully"
    }

    private fun mapToCvResponseDTO(cv: Cv): CvResponseDTO {
        return CvResponseDTO(
            id = cv.id!!,
            yearsOfExperience = cv.yearsOfExperience,
            salaryExpectation = cv.salaryExpectation,
            education = cv.education,
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