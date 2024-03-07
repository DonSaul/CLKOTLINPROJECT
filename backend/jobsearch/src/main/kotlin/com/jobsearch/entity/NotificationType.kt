package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name = "notificationTypes")
data class NotificationType (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    var type: String,

)