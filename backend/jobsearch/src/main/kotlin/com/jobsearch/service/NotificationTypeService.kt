package com.jobsearch.service

import com.jobsearch.dto.NotificationTypeDTO
import com.jobsearch.entity.NotificationType
import com.jobsearch.repository.NotificationTypeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class NotificationTypeService(private val notificationTypeRepository: NotificationTypeRepository) {

    fun findById(id: Int): NotificationTypeDTO {
        val notificationType = notificationTypeRepository.findById(id)
            .orElseThrow { NoSuchElementException("No notification type found with id $id") }
        return NotificationTypeDTO(notificationType.id, notificationType.type)
    }
    fun getAllNotificationTypes(): List<NotificationTypeDTO> {
        val notificationTypes = notificationTypeRepository.findAll()
        return notificationTypes.map { type -> NotificationTypeDTO(type.id, type.type) }
    }


    fun findAllById(ids: Iterable<Int>): List<NotificationType> {
        val notificationTypes = notificationTypeRepository.findAllById(ids)
        return notificationTypes.map { type -> NotificationType(type.id, type.type) }
    }

    fun findByIdAndReturnsEntity(id: Int): NotificationType? {
        val notificationType = notificationTypeRepository.findById(id)
            .orElseThrow { NoSuchElementException("No notification type found with id $id") }
        return NotificationType(notificationType.id, notificationType.type)
    }


}