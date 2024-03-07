package com.jobsearch.controller

import com.jobsearch.dto.ApplicationDTO
import com.jobsearch.service.ApplicationService
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/application")
class ApplicationController(val applicationService: ApplicationService) {

    @PostMapping
    fun createApplication(@Valid @RequestBody applicationDTO: ApplicationDTO): ApplicationDTO {
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
@PreAuthorize("hasRole('manager')")
    @PutMapping("{id}")
    fun updateApplication(
        @PathVariable("id") applicationId: Int,
        @Valid @RequestBody applicationDTO: ApplicationDTO
    ): ApplicationDTO {
        return applicationService.updateApplicationStatus(applicationDTO)
    }

    @DeleteMapping("{id}")
    fun deleteApplication(@PathVariable("id") applicationId: Int): String {
        return applicationService.deleteApplication(applicationId)
    }
}