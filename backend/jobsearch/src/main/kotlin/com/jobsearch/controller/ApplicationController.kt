package com.jobsearch.controller

import com.jobsearch.dto.ApplicationDTO
import com.jobsearch.service.ApplicationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/application")
class ApplicationController(val applicationService: ApplicationService) {

    @PostMapping
    fun createApplication(@RequestBody applicationDTO: ApplicationDTO) : ApplicationDTO{
        return applicationService.createApplication(applicationDTO)
    }
}