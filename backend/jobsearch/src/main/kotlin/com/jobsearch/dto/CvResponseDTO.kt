package com.jobsearch.dto

data class CvResponseDTO(
    val id: Int,
    val summary: String,
    val yearsOfExperience: Int,
    val salaryExpectation: Int,
    val education: String,
    val jobs: Set<JobResponseDTO> = emptySet(),
    val projects: Set<ProjectResponseDTO> = emptySet(),
    val skills: Set<SkillDTO> = emptySet()
)