package com.jobsearch.controller


import com.jobsearch.dto.NotificationDTO
import com.jobsearch.entity.Notification
import com.jobsearch.service.NotificationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/notifications")
class NotificationController(private val notificationService: NotificationService) {

    @PostMapping("/trigger")
    fun triggerNotification(@RequestBody notificationDTO: NotificationDTO): ResponseEntity<String> {
        notificationService.triggerNotification(notificationDTO)
        return ResponseEntity.status(HttpStatus.CREATED).body("Notification triggered successfully")
    }

    @GetMapping("/recipient/{recipientId}")
    fun getNotificationsByRecipientId(@PathVariable recipientId: Int): List<NotificationDTO> {
        return notificationService.getNotificationsByRecipientId(recipientId)
    }

    @GetMapping("/all")
    fun retrieveAllNotifications(): ResponseEntity<List<Notification>> {
        val notifications = notificationService.retrieveAllNotifications()
         return ResponseEntity(notifications, HttpStatus.OK)
    }
}