package com.jobsearch.dto

data class JobResponseDTO (
        val jobId:Int,
        val startDate:String,
        val endDate:String,
        val position:String,
        val description:String,
        val jobFamily:JobFamilyDto
)