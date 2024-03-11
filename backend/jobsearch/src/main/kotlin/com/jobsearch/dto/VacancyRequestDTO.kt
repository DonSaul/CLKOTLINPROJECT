package com.jobsearch.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive


data class VacancyRequestDTO(
    val id: Int?,
    @get:NotBlank(message = "Vacancy name must not be blank")
    val name: String,
    @get:NotBlank(message = "Company name must not be blank")
    val companyName: String,
    @get:Positive(message = "Salary expectation must be a positive number")
    val salaryExpectation: Int,
    @get:Positive(message = "Years of experience must be a positive number")
    val yearsOfExperience: Int,
    @get:NotBlank(message = "Description must not be blank")
    val description: String,
    @get:Positive(message = "Job family id must be a valid number")
    val jobFamilyId: Int,
)