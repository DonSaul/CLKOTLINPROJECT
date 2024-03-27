package com.jobsearch.response

class ErrorResponse(
    val data: String? = null,
    val status: Int = 500,
    val message: String = "Error",
    val timeStamp: Long = System.currentTimeMillis()
)
