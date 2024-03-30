package com.jobsearch.repository

import com.jobsearch.entity.ConversationToken
import org.springframework.data.jpa.repository.JpaRepository

interface ConversationTokenRepository: JpaRepository<ConversationToken, Int> {
    fun findByToken(token: String): ConversationToken
}