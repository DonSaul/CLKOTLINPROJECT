package com.jobsearch.controller

import com.jobsearch.dto.LoginRequest
import com.jobsearch.dto.NotificationDTO
import com.jobsearch.service.NotificationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

    data class ForgotPasswordRequest(
        val email: String
    )
    @PostMapping("/forgotPassword")
    fun forgotPassword(@RequestBody request: ForgotPasswordRequest): ResponseEntity<String>{
        notificationService.sendRecoverPassword(request.email)
        return ResponseEntity.status(HttpStatus.CREATED).body("Notification triggered successfully")
    }
}