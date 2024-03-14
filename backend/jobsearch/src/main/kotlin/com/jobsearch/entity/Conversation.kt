package com.jobsearch.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name="conversations")
data class Conversation(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Int?=null,

        @ManyToOne
        @JoinColumn(name="user1_id", nullable = false)
        val user1: User,

        @ManyToOne
        @JoinColumn(name="user2_id", nullable = false)
        val user2 : User,

    //test
        @OneToMany(mappedBy = "conversation", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JsonManagedReference
        val messages: List<ChatMessage> = mutableListOf()
) {
    @Transient
    fun getLastMessage(): ChatMessage? {
        return messages
                .filter { it.date != null }
                .maxByOrNull { it.date!! }
    }
}