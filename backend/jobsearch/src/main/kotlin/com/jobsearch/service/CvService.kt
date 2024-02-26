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

        val skillIds = cvDTO.longSkillString.split(",").map { it.toInt() }

        // Adding skills to CV
        skillIds.forEach { skillId ->
            val skill = skillRepository.findById(skillId).orElse(null)
            skill?.let {
                cv.skills?.add(it)
            }
        }

        val newCv = cvRepository.save(cv)

        return CvResponseDTO(
            newCv.id!!,
            newCv.yearsOfExperience,
            newCv.salaryExpectation,
            newCv.education,
            projects = newCv.projects?.map { project ->
                ProjectResponseDTO(
                    projectId = project.projectId!!,
                    name = project.name,
                    description = project.description,
                    jobFamily = JobFamilyDto(
                        id = project.jobFamily.id!!,
                        name = project.jobFamily.name
                    )
                )
            }?.toSet() ?: setOf(),
            skills = newCv.skills?.map { SkillDTO(it.skillId!!, it.name) }?.toSet() ?: setOf()
        )
    }


    fun retrieveCv(cvId: Int): CvResponseDTO {
        val cv = cvRepository.findById(cvId)
            .orElseThrow { NoSuchElementException("No CV found with id $cvId") }

        return CvResponseDTO(
            cv.id!!,
            cv.yearsOfExperience,
            cv.salaryExpectation,
            cv.education,
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
            }?.toSet() ?: setOf(),
            skills = cv.skills?.map { SkillDTO(it.skillId!!, it.name) }?.toSet() ?: setOf()
        )
    }

    fun retrieveAllCvs(): List<CvResponseDTO> {
        val cvs = cvRepository.findAll()

        return cvs.map {
            CvResponseDTO(
                it.id!!,
                it.yearsOfExperience,
                it.salaryExpectation,
                it.education,
                projects = it.projects?.map { project ->
                    ProjectResponseDTO(
                        projectId = project.projectId!!,
                        name = project.name,
                        description = project.description,
                        jobFamily = JobFamilyDto(
                            id = project.jobFamily.id!!, // Asegúrate de que jobFamily no sea nulo
                            name = project.jobFamily.name // Asume que JobFamily tiene un campo name
                        )
                    )
                }?.toSet() ?: setOf(),
                skills = it.skills?.map { SkillDTO(it.skillId!!, it.name) }?.toSet() ?: setOf()
            )
        }
    }

    @Transactional
    fun updateCv(cvId: Int, cvDTO: CvRequestDTO): CvResponseDTO {
        val cv = cvRepository.findById(cvId)
            .orElseThrow { NoSuchElementException("No CV found with id $cvId") }

        cv.yearsOfExperience = cvDTO.yearsOfExperience
        cv.salaryExpectation = cvDTO.salaryExpectation
        cv.education = cvDTO.education

        val updatedCv = cvRepository.save(cv)

        return updatedCv.let {
            CvResponseDTO(
                it.id!!,
                it.yearsOfExperience,
                it.salaryExpectation,
                it.education
            )
        }
    }

    fun deleteCv(cvId: Int): String {
        val cv = cvRepository.findById(cvId)
            .orElseThrow { NoSuchElementException("No CV found with id $cvId") }

        cvRepository.delete(cv)

        return "Cv deleted successfully"
    }
}