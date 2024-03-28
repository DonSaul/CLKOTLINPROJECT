package com.jobsearch.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class JobRequestDTO(
    val id: Int?,

    val startDate: LocalDate,
    val endDate: LocalDate,

    @field:NotBlank(message = "Job position must not be blank")
    val position: String,

    @field:NotBlank(message = "Job description must not be blank")
    val description: String,

    @field:NotNull(message = "Job must have a job family")
    val jobFamilyId: Int
)