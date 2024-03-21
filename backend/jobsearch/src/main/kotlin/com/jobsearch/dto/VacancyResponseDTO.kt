package com.jobsearch.dto


data class VacancyResponseDTO(
    val id: Int,
    var name: String,
    val companyName: String,
    val salaryExpectation: Int,
    val yearsOfExperience: Int,
    val description: String,
    val jobFamilyId: Int?,
    val jobFamilyName: String,
    val managerId: Int?
)