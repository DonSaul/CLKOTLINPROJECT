package com.jobsearch.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ProjectRequestDTO (
        val projectId:Int,
        @NotBlank(message = "Project name must not be blank")
        val name:String,
        @NotBlank(message = "Project description must not be blank")
        val description:String,
        @NotNull(message = "Project must have a job family")
        val jobFamilyId:Int
)