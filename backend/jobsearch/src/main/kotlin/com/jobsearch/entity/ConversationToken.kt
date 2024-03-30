package com.jobsearch.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name="conversation_token")
class ConversationToken (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Int? = null,

    @Column(nullable = false, unique = true)
    val token: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    val conversation: Conversation,

    @ManyToOne(fetch = FetchType.LAZY)
    val user: User
)