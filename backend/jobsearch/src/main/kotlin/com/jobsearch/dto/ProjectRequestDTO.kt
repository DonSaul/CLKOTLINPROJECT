package com.jobsearch.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ProjectRequestDTO (
        val id: Int?,

        @field:NotBlank(message = "Project name must not be blank")
        val name: String,

        @field:NotBlank(message = "Project description must not be blank")
        val description:String,

        @field:NotNull(message = "Project must have a job family")
        val jobFamilyId:Int
)