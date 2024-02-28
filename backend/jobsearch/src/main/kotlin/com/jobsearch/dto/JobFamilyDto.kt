package com.jobsearch.dto

import jakarta.validation.constraints.NotBlank

data class JobFamilyDto(
    val id: Int?,
    @get:NotBlank(message = "JobFamily.name must not be blank")
    val name: String,
)