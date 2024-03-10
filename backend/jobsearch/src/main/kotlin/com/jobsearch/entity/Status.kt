package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name = "application_status")
data class Status(
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
        var name: String
)