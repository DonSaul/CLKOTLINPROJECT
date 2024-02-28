package com.jobsearch.entity

import jakarta.persistence.*


@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @Column(nullable = false, length = 45)
    var name: String
)