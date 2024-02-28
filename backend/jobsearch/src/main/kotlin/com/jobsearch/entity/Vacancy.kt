package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name="vacancy")
class Vacancy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    var name: String,
    var companyName: String,
    var salaryExpectation: Int,
    val yearsOfExperience: Int,
    @Lob  // To create a value of type TEXT on db
    var description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_family_id")
    var jobFamily: JobFamily,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    var manager: User
)