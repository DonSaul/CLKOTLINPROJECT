package com.jobsearch.dto

import com.jobsearch.entity.ChatMessage
import com.jobsearch.entity.User

data class ConversationResponseDTO(
        val id: Int?,
        val user1: User,
        val user2: User,
        val lastMessage: ChatMessage?
)