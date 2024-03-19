package com.jobsearch.response

data class StandardResponse<T>(
        val data: T? = null,
        val status: Int = 200,
        val message: String = "Success",
        val timestamp: Long = System.currentTimeMillis()
)