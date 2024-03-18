package com.jobsearch.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class CvRequestDTO(
    val id: Int?,

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

    @field:NotBlank(message = "CV must list at least one skill")
    val longSkillString: String
)