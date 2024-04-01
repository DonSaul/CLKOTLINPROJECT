package com.jobsearch.dto.messaging

import com.jobsearch.entity.Role

data class UserMessageDTO(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role:Role
)