package com.jobsearch.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

data class UserDTO(
    val id: Int? = null,
    @get:NotEmpty(message = "First name must not be empty.")
    val firstName: String,
    @get:NotEmpty(message = "Last name must not be empty.")
    val lastName: String,
    @get:Email(message = "Must be a valid email.")
    val email: String,
    @get:Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
        message = "Password must have minimum eight characters, at least one letter and one number")
    var password: String,
    val roleId: Int?
)