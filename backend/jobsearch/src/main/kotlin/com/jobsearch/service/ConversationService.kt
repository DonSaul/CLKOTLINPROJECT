package com.jobsearch.service

import com.jobsearch.dto.ChatMessageDTO
import com.jobsearch.dto.ChatMessageRequestDTO
import com.jobsearch.dto.ConversationResponseDTO
import com.jobsearch.dto.NotificationDTO
import com.jobsearch.entity.ChatMessage
import com.jobsearch.entity.Conversation
import com.jobsearch.entity.NotificationTypeEnum
import com.jobsearch.entity.User
import com.jobsearch.repository.ChatMessageRepository
import com.jobsearch.repository.ConversationRepository
import com.jobsearch.repository.UserRepository
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.NoSuchElementException

@Service
class ConversationService(
        private val chatMessageRepository: ChatMessageRepository,
        private val userService: UserService,
        private val userRepository: UserRepository,
        private val conversationRepository: ConversationRepository,
        private val notificationService: NotificationService
) {
    fun getAllConversationsForCurrentUser():List<ConversationResponseDTO> {
        val currentUser = userService.retrieveAuthenticatedUser()

        val conversations= conversationRepository.findByUser1IdOrUser2Id(currentUser.id!!, currentUser.id)

        return conversations.map { conversation ->
            ConversationResponseDTO(
                    id = conversation.id,
                    user1 = conversation.user1,
                    user2 = conversation.user2,
                    lastMessage = conversation.getLastMessage()
            )
        }.sortedByDescending { it.lastMessage?.date }
    }

    @Transactional
    fun sendMessage(chatMessageRequestDTO: ChatMessageRequestDTO): ChatMessageDTO {
        val currentUser = userService.retrieveAuthenticatedUser()
        val receiver = userRepository.findByEmail(chatMessageRequestDTO.receiverUserName)
            .orElseThrow { NoSuchElementException("No user found with email ${chatMessageRequestDTO.receiverUserName}") }

        val existingConversation = conversationRepository.findByUser1AndUser2(currentUser, receiver)
            ?: conversationRepository.findByUser1AndUser2(receiver, currentUser)

        val conversation = existingConversation ?: createConversation(currentUser, receiver)

        val chatMessage = ChatMessage(
            sender = currentUser,
            receiver = receiver,
            conversation = conversation,
            message = chatMessageRequestDTO.message,
            date = Date()
        )

        val newChatMessage = chatMessageRepository.save(chatMessage)




        return mapToChatMessageDTO(newChatMessage)
    }
    @Transactional
    fun sendMessageNotification(email: String) {
        val sender = userService.retrieveAuthenticatedUser()
        val receiver = userRepository.findByEmail(email)
            .orElseThrow { NoSuchElementException("No user found with email $email") }
        try {
            if (!isNotificationThrottled(sender.id!!, receiver.id!!)) {
                val notificationDTO = NotificationDTO(
                    type = NotificationTypeEnum.MESSAGES.id,
                    recipient = receiver.id,
                    subject = "New Message",
                    content = "There is a new message sent by: ${receiver.email}",
                    sender = sender.id,
                    vacancy = null
                )
                notificationService.triggerNotification(notificationDTO)
            }
        } catch (e: Exception) {
            println("Error sending notification: ${e.message}")
        }
    }
    @Async
    fun triggerNotificationAsync(senderId: Int, receiverId: Int) {
            val notificationDTO = NotificationDTO(
                type = NotificationTypeEnum.MESSAGES.id,
                recipient = receiverId,
                subject = "New Message",
                content = "There is a new conversation created by: $receiverId ",
                sender = senderId,
                vacancy = null
            )
            notificationService.triggerNotification(notificationDTO)

    }

     fun getCurrentConversationWithUser(email:String) : List<ChatMessage>{
        val currentUser = userService.retrieveAuthenticatedUser()

        val receiver = userRepository.findByEmail(email)
                .orElseThrow { NoSuchElementException("No user found with email $email") }

        val conversationMessages=chatMessageRepository.findMessagesByUserIds(currentUser.id!!,receiver.id!!)

        return conversationMessages

    }

    private fun createConversation(user1: User, user2: User): Conversation {
        val newConversation = Conversation(user1 = user1, user2 = user2)
        val notificationDTO = NotificationDTO(
            type = NotificationTypeEnum.MESSAGES.id,
            recipient = user2.id!!,
            subject = "New Conversation",
            content = "There is a new conversation created by: ${user2.email} ",
            sender = user1.id!!,
            vacancy = null
        )
        notificationService.triggerNotification(notificationDTO)

        return conversationRepository.save(newConversation)
    }

    private fun mapToChatMessageDTO(chatMessage: ChatMessage): ChatMessageDTO {
        return ChatMessageDTO(
                id = chatMessage.id,
                message = chatMessage.message,
                senderUsername = chatMessage.sender.email,
                receiverUsername = chatMessage.receiver.email,
                senderId = chatMessage.sender.id,
                receiverId = chatMessage.receiver.id,
                date = chatMessage.date,
                conversationId = chatMessage.conversation.id!!
        )
    }

    private fun isNotificationThrottled(senderId: Int, receiverId: Int): Boolean {
        val lastNotificationTime = getLastNotificationTime(senderId, receiverId)
        val currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        val minTimeBetweenNotifications = 1 // this is in seconds

        val diffSeconds = lastNotificationTime.let { currentTime - it }

        return diffSeconds < minTimeBetweenNotifications
    }
    private fun getLastNotificationTime(senderId: Int, receiverId: Int): Int {
        val lastNotification = notificationService.findLatestMessageNotification(senderId, receiverId)
        return lastNotification.sentDateTime.toEpochSecond(ZoneOffset.UTC).toInt()
    }

}