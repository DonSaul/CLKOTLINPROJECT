package com.jobsearch.repository

import com.jobsearch.dto.NotificationDTO
import com.jobsearch.entity.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface NotificationRepository: JpaRepository<Notification, Int> {
    @Query("SELECT n FROM Notification n WHERE n.recipient.id = ?1 ORDER BY n.sentDateTime DESC")
    fun getNotificationsByRecipientId(recipientId: Int): List<Notification>

//    @Query("SELECT n FROM Notification n WHERE n.recipient.id = ?1 ORDER BY n.sentDateTime DESC")
//    fun getNotificationsOrderByDate(recipientId: Int): List<Notification>


    fun findFirstBySenderIdAndRecipientIdAndTypeIdOrderBySentDateTimeDesc(senderId: Int, recipientId: Int, typeId: Int): Optional<Notification>
}