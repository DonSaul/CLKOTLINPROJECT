package com.jobsearch.dto

data class JobResponseDTO (
        val jobId:Int,
        val startYear:Int,
        val startMonth:Int,
        val endYear:Int,
        val endMonth:Int,
        val position:String,
        val description:String,
        val jobFamily:JobFamilyDto
)