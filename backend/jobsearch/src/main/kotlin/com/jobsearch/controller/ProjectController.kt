package com.jobsearch.controller

import com.jobsearch.service.ProjectService

class ProjectController(val projectService: ProjectService) {

/*
    @PostMapping
    fun createProject(@RequestBody projectDTO: ProjectDTO): ProjectDTO {
        return projectService.createProject(projectDTO)
    }
    @GetMapping("{id}")
    fun retrieveProject(@PathVariable("id") personId: Int): ProjectDTO {
        return projectService.retrieveProject(personId)
    }

*/
}