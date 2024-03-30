package com.jobsearch.dto.user

import com.jobsearch.entity.NotificationType

data class UserMessageDTO(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val roleId: Int
)