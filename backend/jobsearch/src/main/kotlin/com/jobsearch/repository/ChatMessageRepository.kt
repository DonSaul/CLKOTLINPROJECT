package com.jobsearch.repository

import com.jobsearch.entity.ChatMessage
import com.jobsearch.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface ChatMessageRepository :JpaRepository<ChatMessage,Int>{

    fun findBySenderOrReceiver(sender: User, receiver: User): List<ChatMessage>
}