package com.jobsearch.dto

data class UserResponseDTO(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val roleId: Int,
)