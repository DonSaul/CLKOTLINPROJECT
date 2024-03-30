package com.jobsearch.service

import com.jobsearch.dto.*
import com.jobsearch.entity.*
import com.jobsearch.exception.NotFoundException
import com.jobsearch.repository.ChatMessageRepository
import com.jobsearch.repository.ConversationRepository
import com.jobsearch.repository.ConversationTokenRepository
import com.jobsearch.repository.UserRepository
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantLock


@Service
class ConversationService(
        private val chatMessageRepository: ChatMessageRepository,
        private val userService: UserService,
        private val userRepository: UserRepository,
        private val conversationRepository: ConversationRepository,
        private val notificationService: NotificationService,
        private val conversationTokenRepository: ConversationTokenRepository
) {

    //lock
    object ConversationLockManager {
        private val conversationLocks = ConcurrentHashMap<Pair<String, String>, ReentrantLock>()

        fun getLock(user1Email: String, user2Email: String): ReentrantLock {
            val pair = if (user1Email < user2Email) Pair(user1Email, user2Email) else Pair(user2Email, user1Email)
            return conversationLocks.computeIfAbsent(pair) { ReentrantLock() }
        }
    }
    private fun createConversationLock(user1: User, user2: User): Conversation {
        val lock = ConversationLockManager.getLock(user1.email, user2.email)
        lock.lock()
        try {

            val existingConversation = findExistingConversation(user1, user2)
            if (existingConversation != null) {
                return existingConversation
            }

            val conversation = Conversation(user1 = user1, user2 = user2)
            return conversationRepository.save(conversation)

        } finally {
            lock.unlock()
        }
    }


    //end testing

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

        val existingConversation = findExistingConversation(currentUser, receiver)

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

    private fun findExistingConversation(user1: User, user2: User): Conversation? {
        return conversationRepository.findByUser1AndUser2(user1, user2)
            ?: conversationRepository.findByUser1AndUser2(user2, user1)
    }
    @Transactional
    fun sendMessageNotification(email: String) {
        val sender = userService.retrieveAuthenticatedUser()
        val receiver = userRepository.findByEmail(email)
            .orElseThrow { NoSuchElementException("No user found with email $email") }

        val conversation = conversationRepository.findByUser1AndUser2(sender, receiver);

        val conversationToken = ConversationToken(
            user = receiver,
            conversation = conversation!!
        )

        val savedConversationToken = conversationTokenRepository.save(conversationToken)
        val token = savedConversationToken.token

        val chatUrl = "http://localhost:3000/messaging?token=${token}"

        try {
            if (!isNotificationThrottled(sender.id!!, receiver.id!!)) {
                val notificationDTO = NotificationDTO(
                    type = NotificationTypeEnum.MESSAGES.id,
                    recipient = receiver.id,
                    subject = "New Message",
                    content = """
                        You have a new message sent by: ${sender.email}
                        <br>
                        Please check the message at: $chatUrl
                        <br>
                        Sent at: ${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}
                        """.trimIndent(),
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

    fun getConversationIdByToken(token: String) : ConversationIdDTO {
        val conversationToken = conversationTokenRepository.findByToken(token)

        val authenticatedUser = userService.retrieveAuthenticatedUser()

        if (conversationToken.user != authenticatedUser) {
            throw IllegalAccessException("You cannot access this conversation")
        }

        val conversationId = ConversationIdDTO(conversationToken.conversation.id!!)

        return conversationId
    }

    private fun createConversation(user1: User, user2: User): Conversation {
        val newConversation = Conversation(user1 = user1, user2 = user2)
        val conversation=conversationRepository.save(newConversation)

        val notificationDTO = NotificationDTO(
            type = NotificationTypeEnum.MESSAGES.id,
            recipient = user2.id!!,
            subject = "New Conversation",
            content = "There is a new conversation created by: ${user2.email} ",
            sender = user1.id!!,
            vacancy = null
        )
        notificationService.triggerNotification(notificationDTO)

        return conversation
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
        return try {
            val lastNotification = notificationService.findLatestMessageNotification(senderId, receiverId)
            lastNotification.sentDateTime.toEpochSecond(ZoneOffset.UTC).toInt()
        } catch (e: NotFoundException) {
            0
        }
    }

}