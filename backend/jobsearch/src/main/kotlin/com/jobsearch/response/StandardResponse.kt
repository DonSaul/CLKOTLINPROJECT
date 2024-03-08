package com.jobsearch.response

data class StandardResponse<T>(
        val data: T? = null,
        val statusCode: Int = 200,
        val message: String = "Success"
)