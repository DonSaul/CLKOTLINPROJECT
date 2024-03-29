package com.jobsearch.controller

import com.jobsearch.dto.JobFamilyDto
import com.jobsearch.service.JobFamilyService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/job-family")
@Validated
class JobFamilyController(
    val jobFamilyService: JobFamilyService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createJobFamily(@RequestBody jobFamilyDto: JobFamilyDto): JobFamilyDto {
        return jobFamilyService.createJobFamily(jobFamilyDto)
    }

    @GetMapping("{id}")
    fun retrieveJobFamily(@PathVariable("id") jobFamilyId: Int): JobFamilyDto {
        return jobFamilyService.retrieveJobFamily(jobFamilyId)
    }

    @GetMapping()
    fun retrieveAllJobFamily(): List<JobFamilyDto> {
        return jobFamilyService.retrieveAllJobFamily()
    }

    @PutMapping("{id}")
    fun updateJobFamily(@PathVariable("id") jobFamilyId: Int, @RequestBody jobFamilyDto: JobFamilyDto): JobFamilyDto {
        return jobFamilyService.updateJobFamily(jobFamilyId, jobFamilyDto)
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteJobFamily(@PathVariable("id") jobFamilyId: Int): String {
        return jobFamilyService.deleteJobFamily(jobFamilyId)
    }
}