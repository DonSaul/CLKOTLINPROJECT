package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name = "notification_types")
data class NotificationType (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @Column(nullable = false)
    var type: String,

)

enum class NotificationTypeEnum(val id: Int, val type: String) {
    VACANCIES(1, "vacancies"),
    INVITATIONS(2, "invitations"),
    MESSAGES(3, "messages"),
    FORGOT_PASSWORD(4, "forgotPassword")
}