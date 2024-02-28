package com.jobsearch.controller

import com.jobsearch.dto.ApplicationDTO
import com.jobsearch.service.ApplicationService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/application")
class ApplicationController(val applicationService: ApplicationService) {

    @PostMapping
    fun createApplication(@RequestBody applicationDTO: ApplicationDTO): ApplicationDTO {
        return applicationService.createApplication(applicationDTO)
    }

    @GetMapping("{id}")
    fun retrieveApplication(@PathVariable("id") applicationId: Int): ApplicationDTO {
        return applicationService.retrieveApplication(applicationId)
    }

    @GetMapping
    fun retrieveAllApplication(): List<ApplicationDTO> {
        return applicationService.retrieveAllApplication()
    }

    @PutMapping("{id}")
    fun updateApplication(
        @PathVariable("id") applicationId: Int,
        @RequestBody applicationDTO: ApplicationDTO
    ): ApplicationDTO {
        return applicationService.updateApplication(applicationId, applicationDTO)
    }

    @DeleteMapping("{id}")
    fun deleteApplication(@PathVariable("id") applicationId: Int): String {
        return applicationService.deleteApplication(applicationId)
    }
}