package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name="vacancy")
class Vacancy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val name: String,
    val companyName: String,
    val salaryExpectation: Int,
    @Lob  // To create a value of type TEXT on db
    val description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_family_id")
    val jobFamily: JobFamily? = null
)