package com.jobsearch.dto.override

import com.jobsearch.dto.JobRequestDTO
import com.jobsearch.dto.ProjectRequestDTO
import com.jobsearch.entity.User
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive


data class OverrideCvRequestDTO(

    @field:Positive(message = "Summary field must not be blank")
    val summary: String,

    @field:Positive(message = "CV must have a valid number of years of experience")
    val yearsOfExperience: Int,

    @field:Positive(message = "CV must have a valid salary expectation")
    val salaryExpectation: Int,

    @field:NotBlank(message = "Education field must not be blank")
    val education: String,

    @field:Valid // for validating nested objects
    val jobs: List<JobRequestDTO>,

    @field:Valid // for validating nested objects
    val projects: List<ProjectRequestDTO>,

    @field:NotEmpty(message = "CV must list at least one skill")
    val skillIds: Set<Int>,

    val user:User
)