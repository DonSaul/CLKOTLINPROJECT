package com.jobsearch.config

import java.util.*

class TokenUUID {
    fun generateToken(): String {
        // Generate a random UUID
        val uuid = UUID.randomUUID()
        // Convert UUID to String and remove hyphens
        return uuid.toString().replace("-", "")
    }
}