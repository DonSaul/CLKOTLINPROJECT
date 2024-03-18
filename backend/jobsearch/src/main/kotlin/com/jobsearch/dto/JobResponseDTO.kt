package com.jobsearch.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class JobResponseDTO(
    val id: Int,
    val startYear: Int,
    val startMonth: Int,
    val endYear: Int,
    val endMonth: Int,
    val position: String,
    val description: String,
    val jobFamily: JobFamilyDto
)