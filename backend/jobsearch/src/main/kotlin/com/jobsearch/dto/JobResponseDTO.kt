package com.jobsearch.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class JobResponseDTO(
    val id: Int,

    @JsonFormat(pattern = "yyyy-MM-dd")
    val startDate: LocalDate,

    @JsonFormat(pattern = "yyyy-MM-dd")
    val endDate: LocalDate,

    val position: String,

    val description: String,

    val jobFamily: JobFamilyDto
)