package com.jobsearch.repository

import com.jobsearch.entity.ChatMessage
import com.jobsearch.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChatMessageRepository :JpaRepository<ChatMessage,Int>{

    fun findBySenderOrReceiver(sender: User, receiver: User): List<ChatMessage>

    @Query("SELECT cm FROM ChatMessage cm " +
            "JOIN cm.conversation c " +
            "WHERE (c.user1.id = :userId1 AND c.user2.id = :userId2) OR (c.user1.id = :userId2 AND c.user2.id = :userId1)")
    fun findMessagesByUserIds(userId1: Int, userId2: Int): List<ChatMessage>
}