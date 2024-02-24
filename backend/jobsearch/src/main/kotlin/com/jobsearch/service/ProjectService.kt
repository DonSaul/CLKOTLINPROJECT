package com.jobsearch.service

import com.jobsearch.dto.ProjectDTO
import com.jobsearch.entity.Project
import com.jobsearch.repository.ProjectRepository

class ProjectService(val projectRepository: ProjectRepository) {


    fun createProject(projectDTO: ProjectDTO): ProjectDTO {

        val projectEntity = projectDTO.let {
            Project(projectId  =null, name = it.name, description = it.description)
        }
        val newProject = projectRepository.save(projectEntity)

        return newProject.let {
            ProjectDTO(it.projectId!!, it.name,it.description)
        }
    }

    fun retrieveProject(projectId: Int): ProjectDTO {
        val project = projectRepository.findById(projectId)
                .orElseThrow { NoSuchElementException("No project found with id $projectId") }

        return project.let {
            ProjectDTO(it.projectId!!, it.name, it.description)
        }
    }
/*
    fun retrieveProjectByCv():List<Project>{
        return null
    }
*/

}