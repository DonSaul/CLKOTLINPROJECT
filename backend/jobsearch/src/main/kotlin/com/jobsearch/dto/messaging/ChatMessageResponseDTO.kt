package com.jobsearch.dto.messaging


import java.util.Date

data class ChatMessageResponseDTO(
    val id: Int,
    val date: Date,
    val message: String,
    val sender: UserMessageDTO,
    val receiver:UserMessageDTO,

    )