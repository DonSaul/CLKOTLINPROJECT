package com.jobsearch.service

import com.jobsearch.dto.NotificationDTO
import com.jobsearch.dto.UserDTO
import com.jobsearch.entity.Notification
import com.jobsearch.repository.NotificationRepository
import com.jobsearch.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import com.jobsearch.config.UserMapper
import org.mapstruct.factory.Mappers

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val emailService: EmailService,
    private val userService: UserService,
    private val notificationTypeService: NotificationTypeService,

) {

    fun triggerNotification(notificationDTO: NotificationDTO) {
        val recipientId = notificationDTO.recipient
        val recipient = userService.retrieveUser(recipientId)
        val notification = createNotification(notificationDTO, recipient)
        if (notification != null) {
            sendEmailNotification(notification)
        }
    }

    private fun createNotification(notificationDTO: NotificationDTO, recipient: UserDTO): Notification? {
        val notificationType = notificationDTO.type?.let { notificationTypeService.findById(it) }
        val userMapper = Mappers.getMapper(UserMapper::class.java)
        val recipientEntity = userMapper.toEntity(recipient)
        if (notificationType != null) {
            val notification = Notification(
                type = notificationType,
                recipient = recipientEntity,
                subject = notificationDTO.subject,
                content = notificationDTO.content
            )
            return notificationRepository.save(notification)
        } else {
            println("Notification type not found for ID: ${notificationDTO.type}")
            return null
        }
    }

    private fun sendEmailNotification(notification: Notification) {
        try {
            val emailContent = "Subject: ${notification.subject}\nContent: ${notification.content}"
            emailService.sendEmail(notification.recipient.email, "Notification", emailContent)
            markNotificationAsSent(notification)

        } catch (e: Exception) {
            println("Failed to send email notification: ${e.message}")
        }
    }

    private fun markNotificationAsSent(notification: Notification) {
        notification.sent = true
        notification.sentDateTime = LocalDateTime.now()
        notificationRepository.save(notification)
    }
}