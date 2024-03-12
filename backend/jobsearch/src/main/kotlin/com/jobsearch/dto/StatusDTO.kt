package com.jobsearch.dto

import jakarta.validation.constraints.NotEmpty

data class StatusDTO (
    val statusID: Int?,
    @get:NotEmpty(message = "Name must not be empty.")
    val name: String
)