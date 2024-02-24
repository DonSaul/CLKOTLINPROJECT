package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name="cvs")
data class Cv(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val yearsOfExperience: Int,
    val salaryExpectation: Int,
    val education: String
)