package com.jobsearch.service

import com.jobsearch.dto.NotificationDTO
import com.jobsearch.entity.Notification
import com.jobsearch.repository.NotificationRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import com.jobsearch.entity.NotificationType
import com.jobsearch.entity.User
import com.jobsearch.repository.NotificationTypeRepository
import com.jobsearch.repository.UserRepository
import com.jobsearch.repository.VacancyRepository
import org.springframework.data.repository.findByIdOrNull

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val emailService: EmailService,
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val notificationTypeRepository: NotificationTypeRepository,
    private val vacancyRepository: VacancyRepository
) {
    fun triggerNotification(notificationDTO: NotificationDTO) {
        val recipientId = notificationDTO.recipient
        val recipient = userService.retrieveUser(recipientId)

        if (recipient.notificationActivated) {
            val allowedNotificationTypeIds = recipient.activatedNotificationTypes.map { it?.id }

            val notificationTypeId = notificationDTO.type

            if (allowedNotificationTypeIds.contains(notificationTypeId)) {
                when (notificationTypeId) {
                    1 -> handleNotification(notificationDTO)
                    2 -> handleNotification(notificationDTO)
                    3 -> handleNotification(notificationDTO)
                    else -> println("Unsupported notification type ID: $notificationTypeId")
                }
            } else {
                println("Notification type ID $notificationTypeId is not allowed for user ${recipient.email}. Notification was not sent.")
            }
        } else {
            println("User ${recipient.email} has notifications deactivated. Notification was not sent.")
        }
    }

    private fun handleNotification(notificationDTO: NotificationDTO) {
        val notification = createNotification(notificationDTO)
        sendEmailNotification(notification)
    }

    private fun createNotification(notificationDTO: NotificationDTO): Notification {
        val typeNotification: NotificationType = notificationTypeRepository.findById(notificationDTO.type)
            .orElseThrow { NoSuchElementException("No notification type found with id ${notificationDTO.type}") }
        val recipientNotification: User = userRepository.findById(notificationDTO.recipient)
            .orElseThrow { NoSuchElementException("No user found with id ${notificationDTO.recipient}") }

        //Can be null
        val senderNotification = notificationDTO.sender?.let { userRepository.findByIdOrNull(it) }
        val vacancyNotification = notificationDTO.vacancy?.let { vacancyRepository.findByIdOrNull(it) }

        val notificationToSave = Notification(
            type = typeNotification,
            recipient = recipientNotification,
            subject = notificationDTO.subject,
            content = notificationDTO.content,
            sender = senderNotification,
            vacancy = vacancyNotification
        )

        return notificationRepository.save(notificationToSave)
    }

    //senderEmail
    private fun sendEmailNotification(notification: Notification) {
        try {
            notification.recipient.let { emailService.sendEmail(it.email, notification.subject, notification.content) }
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