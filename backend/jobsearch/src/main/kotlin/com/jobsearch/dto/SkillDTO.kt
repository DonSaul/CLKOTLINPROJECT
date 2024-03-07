package com.jobsearch.dto

import jakarta.validation.constraints.NotEmpty

data class SkillDTO (
        val skillId:Int,
        @NotEmpty(message = "Name must not be empty.")
        val name:String
)