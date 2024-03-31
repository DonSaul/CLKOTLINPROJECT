package com.jobsearch.entity

import jakarta.persistence.*


@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @Column(nullable = false, length = 45, unique = true)
    var name: String
)

enum class RoleEnum(val id: Int, val roleName: String) {
    CANDIDATE(1, "candidate"),
    MANAGER(2, "manager"),
    ADMIN(3, "admin")
}