package com.jobsearch.entity

import com.jobsearch.dto.UserDTO
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "notifications")
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @ManyToOne
    @JoinColumn(name = "notificationType_id")
    var type: NotificationType,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val recipient: User,
    var subject: String,
    var content: String,
    var sentDateTime: LocalDateTime = LocalDateTime.now(),
    var sent: Boolean = false
)