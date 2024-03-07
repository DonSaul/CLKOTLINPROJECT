package com.jobsearch.repository

import com.jobsearch.entity.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository: JpaRepository<Notification, Int> {
}