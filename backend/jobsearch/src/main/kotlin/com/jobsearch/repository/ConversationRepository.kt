package com.jobsearch.repository

import com.jobsearch.entity.Conversation
import com.jobsearch.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface ConversationRepository:JpaRepository<Conversation,Int> {
    fun findByUser1IdOrUser2Id(user1Id:Int,user2Id:Int): List<Conversation>


    fun findByUser1AndUser2(user1: User, user2: User): Conversation?

}