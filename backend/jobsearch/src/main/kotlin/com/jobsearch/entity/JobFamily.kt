package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name = "job_family")
data class JobFamily(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,
    @Column(unique = true)
    var name: String,
)