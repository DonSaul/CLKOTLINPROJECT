package com.jobsearch.controller

import com.jobsearch.dto.ChatMessageDTO
import com.jobsearch.dto.ChatMessageRequestDTO
import com.jobsearch.dto.ConversationIdDTO
import com.jobsearch.dto.ConversationResponseDTO
import com.jobsearch.entity.ChatMessage
import com.jobsearch.repository.UserRepository
import com.jobsearch.service.ConversationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/conversation")
class ConversationController (
    val conversationService: ConversationService,
    private val userRepository: UserRepository,
){



    @GetMapping("/user/all")
    fun getUserConversations(): ResponseEntity<List<ConversationResponseDTO>> {
        val conversations = conversationService.getAllConversationsForCurrentUser()
        return ResponseEntity(conversations, HttpStatus.OK)
    }
    @PostMapping("/send-message")
    fun sendMessage(@RequestBody chatMessageRequestDTO: ChatMessageRequestDTO): ResponseEntity<ChatMessageDTO> {
        val chatMessage= conversationService.sendMessage(chatMessageRequestDTO)
        return ResponseEntity(chatMessage, HttpStatus.OK)
    }

    @GetMapping("/sendMessageNotification/{email}")
    fun sendMessageNotification(@PathVariable email: String): ResponseEntity<Unit> {
        val notificationSent: Unit = conversationService.sendMessageNotification(email)
        return ResponseEntity(notificationSent,HttpStatus.OK)
    }
    @GetMapping("/messages")
    fun getCurrentConversationMessages(@RequestParam email: String): ResponseEntity<List<ChatMessage>>
    {
        val messages= conversationService.getCurrentConversationWithUser(email)
        return ResponseEntity(messages,HttpStatus.OK)
    }

    @GetMapping("/get-conversation-id-by-token")
    fun getConversationIdByToken(@RequestParam token: String): ResponseEntity<ConversationIdDTO>  {
        val conversationId = conversationService.getConversationIdByToken(token)
        return ResponseEntity(conversationId, HttpStatus.OK)
    }
}


