package com.jobsearch.dto


data class Candidate(
    val id: Long,
    val name: String,
    val yearsOfExperience: Int,
    val jobFamily: String,
    val salaryExpectation: Double
)