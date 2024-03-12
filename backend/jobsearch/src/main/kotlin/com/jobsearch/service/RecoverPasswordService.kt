package com.jobsearch.service

import com.jobsearch.dto.NotificationDTO
import com.jobsearch.entity.User
import com.jobsearch.jwt.JwtProvider
import com.jobsearch.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.token.Token
import org.springframework.security.core.token.TokenService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class RecoverPasswordService @Autowired constructor(
    private val notificationService: NotificationService,
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider
){
    fun sendRecoverPassword(email: String){
        try {
            val user = userRepository.findByEmail(email)
                .orElseThrow { NoSuchElementException("No user found with email $email") }
            val userDetails = UserDetailsImpl.build(user)

            val token = jwtProvider.generateJwtToken(userDetails)

            val notificationDTO = NotificationDTO(
                type = 4,
                recipient = user.id!!,
                subject = "Reset Password",
                content = "Instructions for resetting your password: Click this link to reset your password: http://localhost:3000/changepassword?token=$token",
                sender = null,
                vacancy = null
            )

            notificationService.triggerNotification(notificationDTO)
        } catch (e: Exception) {
            println("Failed to send email notification: ${e.message}")
        }
    }

}