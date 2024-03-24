package com.jobsearch.service


import com.jobsearch.config.ExpirableToken
import com.jobsearch.dto.NotificationDTO
import com.jobsearch.entity.NotificationTypeEnum
import com.jobsearch.exception.NotFoundException
import com.jobsearch.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value

import org.springframework.stereotype.Service
import java.time.Duration
import kotlin.NoSuchElementException


@Service
class RecoverPasswordService @Autowired constructor(
    private val notificationService: NotificationService,
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val expirableToken: ExpirableToken
){



    @Value("\${token.expiration.minutes}")
    private val expirationMinutes: Long = 5
    fun sendRecoverPassword(email: String){

        try {
            val user = userRepository.findByEmail(email)
                .orElseThrow { NoSuchElementException("No user found with email $email") }

            val token = expirableToken.generateExpirableToken(Duration.ofMinutes(expirationMinutes))
            userService.updateResetPasswordToken(token, email)

            val notificationDTO = NotificationDTO(
                type = NotificationTypeEnum.FORGOT_PASSWORD.id,
                recipient = user.id!!,
                subject = "Reset Password",
                content = "Instructions for resetting your password: Click this link to reset your password: http://localhost:3000/change-password?token=$token",
                sender = null,
                vacancy = null
            )

            notificationService.triggerNotification(notificationDTO)
        } catch (e: Exception) {
            println("Failed to send email notification: ${e.message}")
        }
    }

    fun changePassword(token: String, newPassword: String) {
        if (expirableToken.isExpired(token)) {
            throw RuntimeException("The provided token has expired")
        }
        val user = userRepository.findByResetPasswordToken(token)
            .orElseThrow { NotFoundException("No user found with token: $token") }
        userService.updatePassword(user, newPassword)
    }



}