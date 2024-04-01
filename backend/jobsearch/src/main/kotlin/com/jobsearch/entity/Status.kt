package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name = "application_status")
data class Status(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column(unique = true)
    var name: String
)

enum class ApplicationStatus(val id: Int, val statusName: String) {
    NOT_APPLIED(1, "Not Applied"),
    APPLIED(2, "Applied"),
}
