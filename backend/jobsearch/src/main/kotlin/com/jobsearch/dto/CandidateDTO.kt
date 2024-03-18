package com.jobsearch.dto

data class CandidateDTO(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val yearsOfExperience: Int,
    val salaryExpectation: Int
)
//    val jobFamilyId: Int,
//    val jobFamilyName: String