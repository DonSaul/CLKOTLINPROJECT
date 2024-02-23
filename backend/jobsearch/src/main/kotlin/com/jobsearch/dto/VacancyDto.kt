package com.jobsearch.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive


data class VacancyDTO(
    val id: Int?,
    @get:NotBlank(message = "Vacancy.name must not be blank")
    val name: String,
    @get:NotBlank(message = "Vacancy.companyName must not be blank")
    val companyName: String,
    @Positive(message = "Vacancy.salaryExpectation must be a positive number")
    val salaryExpectation: Int,
    @get:NotBlank(message = "Vacancy.description must not be blank")
    val description: String,
    val jobFamilyId: Int?,
    val jobFamilyName: String
)