package com.jobsearch.entity

import jakarta.persistence.*



@Entity
@Table(name="users")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    var firstName: String,
    var lastName: String,
    var password: String,
    var email: String,


)