package com.jobsearch.controller

import com.jobsearch.dto.JobFamilyDto
import com.jobsearch.service.JobFamilyService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/job-family")
@Validated
class JobFamilyController(
    val jobFamilyService: JobFamilyService
) {
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun createJobFamily(@Valid @RequestBody jobFamilyDto: JobFamilyDto): JobFamilyDto {
        return jobFamilyService.createJobFamily(jobFamilyDto)
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun retrieveJobFamily(@PathVariable("id") jobFamilyId: Int): JobFamilyDto {
        return jobFamilyService.retrieveJobFamily(jobFamilyId)
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllJobFamily(): List<JobFamilyDto> {
        return jobFamilyService.retrieveAllJobFamily()
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateJobFamily(@PathVariable("id") jobFamilyId: Int, @Valid @RequestBody jobFamilyDto: JobFamilyDto): JobFamilyDto {
        return jobFamilyService.updateJobFamily(jobFamilyId, jobFamilyDto)
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteJobFamily(@PathVariable("id") jobFamilyId: Int): String {
        return jobFamilyService.deleteJobFamily(jobFamilyId)
    }
}