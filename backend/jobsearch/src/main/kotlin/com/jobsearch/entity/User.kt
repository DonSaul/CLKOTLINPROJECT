package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name="Users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var firstName: String,
    var lastName: String,
    var password: String,
    var email: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    var role: Role

)