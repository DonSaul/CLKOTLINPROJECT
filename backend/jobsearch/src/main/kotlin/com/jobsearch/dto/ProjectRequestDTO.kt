package com.jobsearch.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ProjectRequestDTO (
        val id: Int?,

        @field:NotBlank(message = "Project name must not be blank")
        @field:Size(max = 100, message = "Project name must not exceed 100 characters")
        val name: String,

        @field:NotBlank(message = "Project description must not be blank")
        @field:Size(max = 100, message = "Project description must not exceed 100 characters")
        val description:String,

        @field:NotNull(message = "Project must have a job family")
        val jobFamilyId:Int
)