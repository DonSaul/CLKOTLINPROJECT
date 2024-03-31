package com.jobsearch.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class JobRequestDTO(
    val id: Int?,

    val startDate: LocalDate,
    val endDate: LocalDate,

    @field:NotBlank(message = "Job company must not be blank")
    @field:Size(max = 100, message = "Job company must not exceed 100 characters")
    val company: String,

    @field:NotBlank(message = "Job position must not be blank")
    @field:Size(max = 100, message = "Job position must not exceed 100 characters")
    val position: String,

    @field:NotBlank(message = "Job description must not be blank")
    @field:Size(max = 100, message = "Job description must not exceed 100 characters")
    val description: String,

    @field:NotNull(message = "Job must have a job family")
    val jobFamilyId: Int
)