package com.jobsearch.dto

import com.jobsearch.entity.NotificationType
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

data class UserRequestDTO(
    val id: Int? = null,
    @get:NotEmpty(message = "First name must not be empty.")
    val firstName: String,
    @get:NotEmpty(message = "Last name must not be empty.")
    val lastName: String,
    @get:Email(message = "Must be a valid email.")
    val email: String,
    @get:Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}$",
        message = "Password must have minimum four characters, at least one letter and one number")
    var password: String,
    val roleId: Int?,
    var notificationActivated: Boolean = false,
    var activatedNotificationTypes: Set<NotificationType?> = emptySet(),
    var resetPasswordToken: String? = null

)