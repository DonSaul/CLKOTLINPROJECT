package com.jobsearch.dto

data class ApplicationDTO(
    val applicationId: Int? = null,
    val userId: Int,
    val cvId: Int,
    val vacancyId: Int,
    val applicationStatus: String
)
