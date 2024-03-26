package com.jobsearch.dto

import com.jobsearch.entity.JobFamily

data class CandidateDTO(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val yearsOfExperience: Int,
    val salaryExpectation: Int,
    val jobFamilies: List<JobFamily>? = null,
    val aplicationStatus : String? = null
)
