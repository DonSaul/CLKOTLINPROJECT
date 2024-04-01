package com.jobsearch.service


import com.jobsearch.config.ExpirableToken
import com.jobsearch.dto.NotificationDTO
import com.jobsearch.entity.NotificationTypeEnum
import com.jobsearch.exception.NotFoundException
import com.jobsearch.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import java.time.Duration


@Service
class RecoverPasswordService @Autowired constructor(
    private val notificationService: NotificationService,
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val expirableToken: ExpirableToken,
    private val templateEngine: SpringTemplateEngine
){



    @Value("\${token.expiration.minutes}")
    private val expirationMinutes: Long = 5
    fun sendRecoverPassword(email: String){

        try {
            val user = userRepository.findByEmail(email)
                .orElseThrow { NoSuchElementException("No user found with email $email") }

            val token = expirableToken.generateExpirableToken(Duration.ofMinutes(expirationMinutes))
            userService.updateResetPasswordToken(token, email)

            val fragmentContext = Context()

            fragmentContext.setVariable("url", "http://localhost:3000/change-password?token=$token")

            val fragmentHtml = templateEngine.process("recoverPasswordTemplate", fragmentContext)

            val templateContext = Context()

            templateContext.setVariable("targetName", "${user.firstName} ${user.lastName}")
            templateContext.setVariable("content", fragmentHtml)

            val emailContent = templateEngine.process("emailTemplate", templateContext)

            val notificationDTO = NotificationDTO(
                type = NotificationTypeEnum.FORGOT_PASSWORD.id,
                recipient = user.id!!,
                subject = "Reset Password",
                content = "Instructions for resetting your password: Click this link to reset your password: http://localhost:3000/change-password?token=$token",
                sender = null,
                vacancy = null,
                emailContent = emailContent
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