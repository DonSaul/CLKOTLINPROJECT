package com.jobsearch.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.Range

data class CvRequestDTO(

    @field:NotBlank(message = "Summary field must not be blank")
    @field:Size(max = 500, message = "Summary field must not exceed 500 characters")
    val summary: String,

    // candidate could not have years of experience, min can be 0
    @field:Range(min = 0, max = 99, message = "CV must have a valid number of years of experience between 0 and 99")
    val yearsOfExperience: Int,

    @field:Positive(message = "CV must have a valid salary expectation")
    val salaryExpectation: Int,

    @field:NotBlank(message = "Education field must not be blank")
    @field:Size(max = 100, message = "Education field must not exceed 100 characters")
    val education: String,

    @field:Valid // for validating nested objects
    val jobs: List<JobRequestDTO>,

    @field:Valid // for validating nested objects
    val projects: List<ProjectRequestDTO>,

    @field:NotEmpty(message = "CV must list at least one skill")
    val skillIds: Set<Int>
)