package com.jobsearch.dto

import jakarta.validation.constraints.NotEmpty

data class LoginRequest(
    @get:NotEmpty(message = "Email must not be empty.")
    val username: String,
    @get:NotEmpty(message = "Password must not be empty.")
    val password: String
)

