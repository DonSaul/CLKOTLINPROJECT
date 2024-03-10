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