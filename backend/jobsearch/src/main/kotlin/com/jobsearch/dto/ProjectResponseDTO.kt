package com.jobsearch.dto

data class ProjectResponseDTO (
        val id: Int,
        val name: String,
        val description: String,
        val jobFamily: JobFamilyDto
)