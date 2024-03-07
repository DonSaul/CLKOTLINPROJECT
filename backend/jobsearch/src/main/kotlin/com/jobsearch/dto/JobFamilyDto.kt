package com.jobsearch.dto

import jakarta.validation.constraints.NotEmpty

data class JobFamilyDto(
    val id: Int?,
    @NotEmpty(message = "Name must not be blank")
    val name: String,
)