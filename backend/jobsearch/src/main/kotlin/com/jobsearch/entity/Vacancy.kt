package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name="vacancy")
data class Vacancy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    var name: String,
    var companyName: String,
    var salaryExpectation: Int,
    @Lob  // To create a value of type TEXT on db
    var description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_family_id")
    var jobFamily: JobFamily
)