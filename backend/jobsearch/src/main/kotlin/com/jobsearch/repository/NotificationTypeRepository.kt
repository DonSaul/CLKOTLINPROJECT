package com.jobsearch.repository

import com.jobsearch.entity.NotificationType
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationTypeRepository:JpaRepository<NotificationType, Int> {
}