package com.jobsearch.dto

import com.jobsearch.entity.NotificationType

data class UserDTO(
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    var password: String,
    val roleId: Int?,
    var notificationActivated: Boolean = false,
    var activatedNotificationTypes: Set<NotificationType?> = emptySet()
)