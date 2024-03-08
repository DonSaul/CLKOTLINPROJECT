package com.jobsearch.service

import com.jobsearch.dto.ChatMessageDTO
import com.jobsearch.dto.ChatMessageRequestDTO
import com.jobsearch.entity.ChatMessage
import com.jobsearch.repository.ChatMessageRepository
import com.jobsearch.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException
@Service
class ChatMessageService
(

        private val chatMessageRepository: ChatMessageRepository,
        private val userService: UserService,
        private val userRepository:UserRepository
        )
{

        fun getAllMessagesForCurrentUser(): List<ChatMessage> {
                val currentUser = userService.retrieveAuthenticatedUser()
                return chatMessageRepository.findBySenderOrReceiver(currentUser, currentUser)
        }
//conversations
        fun getAllConversationsForCurrentUser():List<ChatMessage> {
            val currentUser = userService.retrieveAuthenticatedUser()
            return chatMessageRepository.findBySenderOrReceiver(currentUser, currentUser)
        }

        fun sendMessage(chatMessageRequestDTO: ChatMessageRequestDTO):ChatMessageDTO {

                val currentUser = userService.retrieveAuthenticatedUser()
                val receiver = userRepository.findByEmail(chatMessageRequestDTO.receiverUserName)
                        .orElseThrow { NoSuchElementException("No user found with email ${chatMessageRequestDTO.receiverUserName}") }

                val chatMessage = ChatMessage(
                        sender = currentUser,
                        receiver = receiver,
                        message = chatMessageRequestDTO.message,
                        date = Date()
                )


                val newChatMessage=chatMessageRepository.save(chatMessage)

                return mapToChatMessageDTO(newChatMessage)

        }






       private fun mapToChatMessageDTO(chatMessage: ChatMessage): ChatMessageDTO {
                return ChatMessageDTO(
                        id = chatMessage.id,
                        message = chatMessage.message,
                        senderUsername = chatMessage.sender.email,
                        receiverUsername = chatMessage.receiver.email,
                        senderId = chatMessage.sender.id,
                        receiverId = chatMessage.receiver.id,
                        date = chatMessage.date
                )
        }


}