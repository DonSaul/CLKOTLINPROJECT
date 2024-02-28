package com.jobsearch.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive


data class VacancyDto(
    val id: Int?,
    @get:NotBlank(message = "Vacancy.name must not be blank")
    val name: String,
    @get:NotBlank(message = "Vacancy.companyName must not be blank")
    val companyName: String,
    @Positive(message = "Vacancy.salaryExpectation must be a positive number")
    val salaryExpectation: Int,
    @Positive(message = "Vacancy.yearsOfExperience must be a positive number")
    val yearsOfExperience: Int,
    @get:NotBlank(message = "Vacancy.description must not be blank")
    val description: String,
    val jobFamilyId: Int?,
    val jobFamilyName: String? = null,
    val managerId: Int?
)