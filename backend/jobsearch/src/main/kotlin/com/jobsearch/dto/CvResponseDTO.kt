package com.jobsearch.dto

data class CvResponseDTO(
    val id: Int,
    val yearsOfExperience: Int,
    val salaryExpectation: Int,
    val education: String,
    val projects: Set<ProjectResponseDTO> = emptySet(),
    val skills: Set<SkillDTO> = emptySet()
)
