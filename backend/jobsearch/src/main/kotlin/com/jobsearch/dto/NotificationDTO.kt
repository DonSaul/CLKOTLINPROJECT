package com.jobsearch.dto


import java.time.LocalDateTime

data class NotificationDTO(
    val id: Int? = null,
    var type: NotificationTypeDTO,
    val recipient: Int,
    var subject: String,
    var content: String,
    var sentDateTime: LocalDateTime = LocalDateTime.now(),
    var sent: Boolean = false
)