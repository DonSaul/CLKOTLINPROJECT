package com.jobsearch.dto

data class ProjectResponseDTO (
        val projectId:Int,
        val name:String,
        val description:String,
        val jobFamily:JobFamilyDto
)