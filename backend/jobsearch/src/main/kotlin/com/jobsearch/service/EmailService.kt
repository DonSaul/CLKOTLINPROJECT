package com.jobsearch.service

import jakarta.mail.MessagingException
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(private val mailSender: JavaMailSender) {

    @Throws(MailException::class, MessagingException::class)
    fun sendEmail(to: String, subject: String, content: String) {
        try {
            val mimeMessage: MimeMessage = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(mimeMessage, false, "utf-8")
            helper.setTo(to)
            helper.setSubject(subject)
            helper.setText(content, true)
            mailSender.send(mimeMessage)
            println("Email sent successfully to $to")
        } catch (ex: Exception) {
            println("Failed to send email to $to: ${ex.message}")
            throw ex
        }
    }
}