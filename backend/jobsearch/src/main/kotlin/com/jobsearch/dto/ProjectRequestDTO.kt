package com.jobsearch.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ProjectRequestDTO (
        val projectId:Int,
        @get:NotBlank(message = "Project name must not be blank")
        val name:String,
        @get:NotBlank(message = "Project description must not be blank")
        val description:String,
        @get:NotNull(message = "Project must have a job family")
        val jobFamilyId:Int
)