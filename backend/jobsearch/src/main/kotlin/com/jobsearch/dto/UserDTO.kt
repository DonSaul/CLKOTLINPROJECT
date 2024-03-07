package com.jobsearch.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

data class UserDTO(
    val id: Int? = null,
    @NotEmpty(message = "First name must not be empty.")
    val firstName: String,
    @NotEmpty(message = "Last name must not be empty.")
    val lastName: String,
    @Email(message = "Must be a valid email.")
    val email: String,
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
        message = "Password must have Minimum eight characters, at least one letter and one number")
    var password: String,
    val roleId: Int?
)