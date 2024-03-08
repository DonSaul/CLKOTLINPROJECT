package com.jobsearch.dto


import com.jobsearch.entity.NotificationType
import com.jobsearch.entity.User
import java.time.LocalDateTime

data class NotificationDTO(
    val id: Int? = null,
    var type: Int?,
    var recipient: Int,
    var subject: String,
    var content: String,
    var sentDateTime: LocalDateTime = LocalDateTime.now(),
    var sent: Boolean = false
)