package com.jobsearch.controller

import com.jobsearch.dto.ChatMessageDTO
import com.jobsearch.dto.ChatMessageRequestDTO
import com.jobsearch.dto.ConversationResponseDTO
import com.jobsearch.entity.ChatMessage
import com.jobsearch.service.ChatMessageService
import com.jobsearch.service.ConversationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/v1/chat")
class ChatMessageController(
        val chatMessageService: ChatMessageService,
        val conversationService: ConversationService
) {




/*
    @PostMapping("/send-message")
    fun sendMessage(@RequestBody chatMessageRequestDTO: ChatMessageRequestDTO): ChatMessageDTO {


        return chatMessageService.sendMessage(chatMessageRequestDTO)
    }

*/


}