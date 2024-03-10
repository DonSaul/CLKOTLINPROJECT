package com.jobsearch.dto

data class ApplicationDTO(
    val applicationId: Int? = null,
    val userId: Int? = null,
    val cvId: Int? = null,
    val vacancyId: Int,
    val applicationStatus: String?=null,
    val applicationStatusId: Int?=null,
)
