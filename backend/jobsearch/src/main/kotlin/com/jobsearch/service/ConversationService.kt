package com.jobsearch.service

import com.jobsearch.dto.ChatMessageDTO
import com.jobsearch.dto.ChatMessageRequestDTO
import com.jobsearch.dto.ConversationResponseDTO
import com.jobsearch.dto.NotificationDTO
import com.jobsearch.dto.messaging.ChatMessageResponseDTO
import com.jobsearch.dto.messaging.UserMessageDTO
import com.jobsearch.entity.ChatMessage
import com.jobsearch.entity.Conversation
import com.jobsearch.entity.NotificationTypeEnum
import com.jobsearch.entity.User
import com.jobsearch.exception.NotFoundException
import com.jobsearch.repository.ChatMessageRepository
import com.jobsearch.repository.ConversationRepository
import com.jobsearch.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import java.time.LocalDateTime
import java.time.ZoneOffset
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
        private val templateEngine: SpringTemplateEngine
) {

    //lock
    object ConversationLockManager {
        private val conversationLocks = ConcurrentHashMap<Pair<String, String>, ReentrantLock>()

        fun getLock(user1Email: String, user2Email: String): ReentrantLock {
            val pair = if (user1Email < user2Email) Pair(user1Email, user2Email) else Pair(user2Email, user1Email)
            return conversationLocks.computeIfAbsent(pair) { ReentrantLock() }
        }
    }

    @Transactional
    private fun createConversationLock(user1: User, user2: User): Conversation {
        val lock = ConversationLockManager.getLock(user1.email, user2.email)
        lock.lock()
        try {

            val existingConversation = findExistingConversation(user1, user2)

            if (existingConversation != null) {
                return existingConversation
            }

            val newConversation = Conversation(user1 = user1, user2 = user2)
            val conversation=conversationRepository.save(newConversation)

            //using coroutine
            CoroutineScope(Dispatchers.Default).launch {

                // Setting html email
                val fragmentContext = Context()

                fragmentContext.setVariable("senderEmail", user1.email)
                fragmentContext.setVariable("url", "http://localhost:3000/messaging/${user1.id}")

                val fragmentHtml = templateEngine.process("newConversationCreatedTemplate", fragmentContext)

                val templateContext = Context()

                templateContext.setVariable("targetName", "${user2.firstName} ${user2.lastName}")
                templateContext.setVariable("content", fragmentHtml)

                val emailContent = templateEngine.process("emailTemplate", templateContext)

                notificationService.triggerNotification(NotificationDTO(
                    type = NotificationTypeEnum.MESSAGES.id,
                    recipient = user2.id!!,
                    subject = "New Conversation",
                    content = "There is a new conversation created by: ${user1.email} ",
                    sender = user1.id!!,
                    vacancy = null,
                    emailContent = emailContent
                ))
            }

            return conversation

        } finally {
            lock.unlock()
        }
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

    //end testing

    fun getAllConversationsForCurrentUser():List<ConversationResponseDTO> {
        val currentUser = userService.retrieveAuthenticatedUser()

        val conversations= conversationRepository.findByUser1IdOrUser2Id(currentUser.id!!, currentUser.id)

        return conversations.map { conversation ->

            val user1DTO = UserMessageDTO(
                id = conversation.user1.id!!,
                firstName = conversation.user1.firstName,
                lastName = conversation.user1.lastName,
                email = conversation.user1.email,
                role = conversation.user1.role!!
            )
            val user2DTO = UserMessageDTO(
                id = conversation.user2.id!!,
                firstName = conversation.user2.firstName,
                lastName = conversation.user2.lastName,
                email = conversation.user2.email,
                role = conversation.user2.role!!
            )
            ConversationResponseDTO(
                id = conversation.id,
                user1 = user1DTO,
                user2 = user2DTO,
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

        val conversation = existingConversation ?: createConversationLock(currentUser, receiver)



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

        // Setting html email
        val fragmentContext = Context()

        fragmentContext.setVariable("senderEmail", sender.email)
        fragmentContext.setVariable("url", "http://localhost:3000/messaging/${sender.id}")

        val fragmentHtml = templateEngine.process("messageRecievedTemplate", fragmentContext)

        val templateContext = Context()

        templateContext.setVariable("targetName", "${receiver.firstName} ${receiver.lastName}")
        templateContext.setVariable("content", fragmentHtml)

        val emailContent = templateEngine.process("emailTemplate", templateContext)

        try {
            if (!isNotificationThrottled(sender.id!!, receiver.id!!)) {
                val notificationDTO = NotificationDTO(
                    type = NotificationTypeEnum.MESSAGES.id,
                    recipient = receiver.id,
                    subject = "New Message",
                    content = "There is a new message sent by: ${sender.email}",
                    sender = sender.id,
                    vacancy = null,
                    emailContent = emailContent
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

     fun getCurrentConversationWithUser(email:String) : List<ChatMessageResponseDTO>{
        val currentUser = userService.retrieveAuthenticatedUser()

        val receiver = userRepository.findByEmail(email)
                .orElseThrow { NoSuchElementException("No user found with email $email") }

        val conversationMessages=chatMessageRepository.findMessagesByUserIds(currentUser.id!!,receiver.id!!)

         return conversationMessages.map { chatMessage ->
             val senderDTO = UserMessageDTO(
                 id = chatMessage.sender.id!!,
                 firstName = chatMessage.sender.firstName,
                 lastName = chatMessage.sender.lastName,
                 email = chatMessage.sender.email,
                 role = chatMessage.sender.role!!
             )
             val receiverDTO = UserMessageDTO(
                 id = chatMessage.receiver.id!!,
                 firstName = chatMessage.receiver.firstName,
                 lastName = chatMessage.receiver.lastName,
                 email = chatMessage.receiver.email,
                 role = chatMessage.receiver.role!!
             )
             ChatMessageResponseDTO(
                 id = chatMessage.id!!,
                 date = chatMessage.date,
                 message = chatMessage.message,
                 sender = senderDTO,
                 receiver = receiverDTO
             )
         }

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