package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name = "status")
data class Status(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var statusId: Int? = null,
    var name: String
)