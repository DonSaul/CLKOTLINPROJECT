package com.jobsearch.dto

import com.jobsearch.entity.NotificationType

data class UserResponseDTO(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val roleId: Int,
    var notificationActivated: Boolean = false,
    var activatedNotificationTypes: Set<NotificationType?> = emptySet(),
    var resetPasswordToken: String? = null
)