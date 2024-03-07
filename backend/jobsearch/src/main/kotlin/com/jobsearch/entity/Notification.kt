package com.jobsearch.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "notifications")
data class Notification (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @ManyToOne
    var type: NotificationType,
    @ManyToOne
    val recipient: User,
    var subject: String,
    var content: String,
    var sentDateTime: LocalDateTime = LocalDateTime.now(),
    var sent: Boolean = false
)