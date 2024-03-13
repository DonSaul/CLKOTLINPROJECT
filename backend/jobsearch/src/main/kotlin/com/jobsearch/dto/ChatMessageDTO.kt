package com.jobsearch.dto

import java.util.*

data class ChatMessageDTO(

    val id: Int? = null,
    val message: String,
    val senderUsername: String?,
    val receiverUsername: String?,
    val senderId:Int?,
    val receiverId:Int?,
    val date: Date,
    val conversationId:Int,

)