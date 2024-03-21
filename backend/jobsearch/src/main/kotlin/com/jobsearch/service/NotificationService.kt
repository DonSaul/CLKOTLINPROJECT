package com.jobsearch.service

import com.jobsearch.dto.NotificationDTO
import com.jobsearch.entity.Notification
import com.jobsearch.repository.NotificationRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import com.jobsearch.entity.NotificationType
import com.jobsearch.entity.NotificationTypeEnum
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

        if (recipient.notificationActivated || notificationDTO.type == NotificationTypeEnum.FORGOT_PASSWORD.id) {
            val allowedNotificationTypeIds = recipient.activatedNotificationTypes.map { it?.id }

            val notificationTypeId = notificationDTO.type

            if (allowedNotificationTypeIds.contains(notificationTypeId)|| notificationDTO.type == NotificationTypeEnum.FORGOT_PASSWORD.id) {
                when (notificationTypeId) {
                    NotificationTypeEnum.VACANCIES.id -> handleNotification(notificationDTO)
                    NotificationTypeEnum.INVITATIONS.id -> handleNotification(notificationDTO)
                    NotificationTypeEnum.MESSAGES.id -> handleNotification(notificationDTO)
                    NotificationTypeEnum.FORGOT_PASSWORD.id -> handleNotification(notificationDTO)
                    else -> println("Unsupported notification type ID: $notificationTypeId")
                }
            } else {
                println("Notification type ID $notificationTypeId is not allowed for user ${recipient.email}. Notification was not sent.")
            }
        } else {
            println("User ${recipient.email} has notifications deactivated. Notification was not sent.")
        }
    }
    fun retrieveAllNotifications(): List<Notification> {
        return notificationRepository.findAll()
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
    fun getNotificationsByRecipientUsername(email: String): List<Notification> {
        val user = userRepository.findByEmail(email)
            .orElseThrow { NoSuchElementException("Could not find any user with the email $email") }
        val notifications = notificationRepository.getNotificationsByRecipientId(user.id!!)
            .filter { it.type.id != NotificationTypeEnum.FORGOT_PASSWORD.id }


        return notifications.map { mapToNotification(it) }
    }
    fun findLatestMessageNotification(senderId: Int, recipientId: Int): NotificationDTO {
        val typeId = NotificationTypeEnum.MESSAGES.id
        val latestNotification = notificationRepository.findFirstBySenderIdAndRecipientIdAndTypeIdOrderBySentDateTimeDesc(senderId, recipientId, typeId)
            .orElseThrow { NoSuchElementException("No latest notification found") }
        return mapToDto(latestNotification)
    }
    fun mapToDto(notification: Notification): NotificationDTO {
        return notification.let {
            NotificationDTO(
                id = it.id,
                type = it.type.id!!,
                recipient = it.recipient.id!!,
                subject = it.subject,
                content = it.content,
                sentDateTime = it.sentDateTime,
                sent = it.sent,
                sender = it.sender?.id,
                vacancy = it.vacancy?.id
            )
        }
    }
    fun mapToNotification(notification: Notification): Notification {
        //sender
        val senderId = notification.sender?.id
            ?: throw NoSuchElementException("Sender id is null for notification id ${notification.id}")

        val sender = userRepository.findById(senderId)
            .map { it.copy(password = null.toString()) }
            .orElseThrow { NoSuchElementException("Could not find any user with the id $senderId") }

        //recipient
        val recipientId = notification.recipient.id
            ?: throw NoSuchElementException("Recipient id is null for notification id ${notification.id}")
        val recipient = userRepository.findById(recipientId)
            .map { it.copy(password = null.toString()) }
            .orElseThrow { NoSuchElementException("Could not find any user with the id $recipientId") }

        return Notification(
            id = notification.id,
            type = notification.type,
            recipient = recipient,
            subject = notification.subject,
            content = notification.content,
            sentDateTime = notification.sentDateTime,
            sent = notification.sent,
            sender = sender,
            vacancy = notification.vacancy
        )
    }
}