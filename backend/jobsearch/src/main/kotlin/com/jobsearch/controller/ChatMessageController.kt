package com.jobsearch.controller

import com.jobsearch.dto.ChatMessageDTO
import com.jobsearch.dto.ChatMessageRequestDTO
import com.jobsearch.entity.ChatMessage
import com.jobsearch.service.ChatMessageService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/v1/chat")
class ChatMessageController(
        val chatMessageService: ChatMessageService
) {
    @GetMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    fun getAllMessagesForCurrentUser(): List<ChatMessage> {
        return chatMessageService.getAllMessagesForCurrentUser()
    }

    @PostMapping("/send-message")
    @ResponseStatus(HttpStatus.CREATED)
    fun sendMessage(@RequestBody chatMessageRequestDTO: ChatMessageRequestDTO): ChatMessageDTO {
        return chatMessageService.sendMessage(chatMessageRequestDTO)
    }
}