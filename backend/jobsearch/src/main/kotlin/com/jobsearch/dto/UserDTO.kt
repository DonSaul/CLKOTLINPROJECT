package com.jobsearch.dto

data class UserDTO(
    val id : Long? = null,
    val firstName : String,
    val lastName : String,
    val email : String,
    val password : String,
    val role : Int
)