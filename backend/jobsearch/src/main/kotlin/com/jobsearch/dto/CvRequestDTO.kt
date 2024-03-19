package com.jobsearch.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive

data class CvRequestDTO(
    val id: Int?,
    @get:Positive(message = "CV must have a valid number of years of experience")
    val yearsOfExperience: Int,
    @get:Positive(message = "CV must have a valid salary expectation")
    val salaryExpectation: Int,
    @get:NotBlank(message = "Education field must not be blank")
    val education: String,
    @get:NotEmpty(message = "CV must include at least one project")
    val projects: List<ProjectRequestDTO>,
    @get:NotBlank(message = "CV must list at least one skill")
    val longSkillString: String
)