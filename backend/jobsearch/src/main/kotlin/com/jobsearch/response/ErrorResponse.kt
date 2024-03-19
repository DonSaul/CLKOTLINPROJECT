package com.jobsearch.response

class ErrorResponse<T>(
    val data: T? = null,
    val status: Int = 500,
    val message: String = "Error",
    val timeStamp: Long = System.currentTimeMillis()
)