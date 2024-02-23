package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name = "job_family")
class JobFamily(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val name: String,
)