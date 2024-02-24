package com.jobsearch.controller

import com.jobsearch.dto.ProjectDTO
import com.jobsearch.service.ProjectService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

class ProjectController(val projectService: ProjectService) {


    @PostMapping
    fun createProject(@RequestBody projectDTO: ProjectDTO): ProjectDTO {
        return projectService.createProject(projectDTO)
    }
    @GetMapping("{id}")
    fun retrieveProject(@PathVariable("id") personId: Int): ProjectDTO {
        return projectService.retrieveProject(personId)
    }


}