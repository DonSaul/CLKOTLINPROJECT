package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name="person")
data class Person (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var firstName: String,
    var lastName: String
)