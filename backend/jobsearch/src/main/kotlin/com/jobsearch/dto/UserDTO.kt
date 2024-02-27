package com.jobsearch.dto

import com.jobsearch.entity.Role

data class UserDTO(
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val roleId: Int?
)