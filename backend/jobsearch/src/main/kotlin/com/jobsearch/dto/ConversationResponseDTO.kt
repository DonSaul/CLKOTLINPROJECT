package com.jobsearch.dto

import com.jobsearch.dto.messaging.UserMessageDTO
import com.jobsearch.entity.ChatMessage

data class ConversationResponseDTO(
        val id: Int?,
        val user1: UserMessageDTO,
        val user2: UserMessageDTO,
        val lastMessage: ChatMessage?
)