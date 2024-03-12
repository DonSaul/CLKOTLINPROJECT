package com.jobsearch.response

class ErrorResponse(
    val status: Int,
    val message: String,
    val timeStamp: Long
)