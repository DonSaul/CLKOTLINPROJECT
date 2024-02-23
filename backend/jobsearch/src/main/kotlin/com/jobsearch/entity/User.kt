package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name="Users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var firstName: String,
    var lastName: String,
    var password: String,
    var email: String
)