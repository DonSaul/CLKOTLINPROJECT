package com.jobsearch.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class JobRequestDTO (
        val jobId: Int,

        @field:NotNull(message = "Start year must not be null")
        @field:Min(value = 1900, message = "Start year must be reasonable")
        @field:Max(value = 2100, message = "Start year must be reasonable")
        val startYear: Int,

        @field:NotNull(message = "Start month must not be null")
        @field:Min(value = 1, message = "Start month must be between 1 and 12")
        @field:Max(value = 12, message = "Start month must be between 1 and 12")
        val startMonth: Int,

        @field:NotNull(message = "End year must not be null")
        @field:Min(value = 1900, message = "End year must be reasonable")
        @field:Max(value = 2100, message = "End year must be reasonable")
        val endYear: Int,

        @field:NotNull(message = "End month must not be null")
        @field:Min(value = 1, message = "End month must be between 1 and 12")
        @field:Max(value = 12, message = "End month must be between 1 and 12")
        val endMonth: Int,

        @field:NotBlank(message = "Job position must not be blank")
        val position: String,

        @field:NotBlank(message = "Job description must not be blank")
        val description: String,

        @field:NotNull(message = "Job must have a job family")
        val jobFamilyId: Int
)