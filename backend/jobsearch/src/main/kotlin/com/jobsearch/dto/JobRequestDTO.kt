package com.jobsearch.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class JobRequestDTO (
        val jobId:Int,
        @get:NotBlank(message = "Job start date must not be blank")
        val startDate:String,
        @get:NotBlank(message = "Job end date must not be blank")
        val endDate:String,
        @get:NotBlank(message = "Job position must not be blank")
        val position:String,
        @get:NotBlank(message = "Job description must not be blank")
        val description:String,
        @get:NotNull(message = "Job must have a job family")
        val jobFamilyId:Int
)