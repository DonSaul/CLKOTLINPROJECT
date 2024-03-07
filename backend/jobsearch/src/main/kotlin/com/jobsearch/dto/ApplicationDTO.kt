package com.jobsearch.dto

import jakarta.validation.constraints.Positive

data class ApplicationDTO(
    val applicationId: Int? = null,
    val userId: Int? = null,
    val cvId: Int? = null,
    @Positive(message = "Vacancy Id must be a valid number")
    val vacancyId: Int,
    val applicationStatus: String?=null,
    val applicationStatusId: Int?=null,
)
