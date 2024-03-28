package com.jobsearch.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name="chat_messages")
data class ChatMessage(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @Column(columnDefinition = "TEXT")
        val message: String,


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "sender_id", nullable = false)
        val sender:User,


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "receiver_id", nullable = false)
        val receiver:User,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(nullable = false)
        val date: Date,

        //test
        @ManyToOne(fetch = FetchType.LAZY)
        @JsonBackReference
        @JoinColumn(name = "conversation_id")
        val conversation: Conversation


        ) {
}