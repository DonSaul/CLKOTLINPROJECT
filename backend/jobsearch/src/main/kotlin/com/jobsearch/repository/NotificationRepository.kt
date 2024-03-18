package com.jobsearch.repository

import com.jobsearch.dto.NotificationDTO
import com.jobsearch.entity.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface NotificationRepository: JpaRepository<Notification, Int> {
    fun getNotificationsByRecipientId(recipientId: Int): List<Notification>

    fun findFirstBySenderIdAndRecipientIdAndTypeIdOrderBySentDateTimeDesc(senderId: Int, recipientId: Int, typeId: Int): Optional<Notification>
}