package com.jobsearch.dto

data class CvRequestDTO(
    val id: Int?,
    val yearsOfExperience: Int,
    val salaryExpectation: Int,
    val education: String,
    val projects: List<ProjectRequestDTO>,
    val longSkillString: String
)