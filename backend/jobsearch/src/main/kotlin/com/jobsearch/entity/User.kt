package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    var firstName: String,
    var lastName: String,
    var password: String,
    @Column(unique = true)
    var email: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    var role: Role? = null,

    @Column(name = "notification_activated", nullable = false)
    var notificationActivated: Boolean = false,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_notification_type",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "id")]
    )
    var activatedNotificationTypes: Set<NotificationType?> = emptySet(),

    @Lob
    @Column(name = "reset_password_token")
    var resetPasswordToken: String?
)