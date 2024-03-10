package com.jobsearch.dto


import java.time.LocalDateTime

data class NotificationDTO(
    val id: Int? = null,
    var type: Int,
    var recipient: Int,
    var subject: String,
    var content: String,
    var sentDateTime: LocalDateTime = LocalDateTime.now(),
    var sent: Boolean = false,
    var sender: Int? = null,
    var vacancy: Int? = null
)