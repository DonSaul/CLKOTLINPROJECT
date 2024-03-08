package com.jobsearch.service

import com.jobsearch.entity.NotificationType
import com.jobsearch.repository.NotificationTypeRepository
import org.springframework.stereotype.Service

@Service
class NotificationTypeService(private val notificationTypeRepository: NotificationTypeRepository){

    fun findById(id: Int): NotificationType? {
        return notificationTypeRepository.findById(id).orElse(null)
    }
}