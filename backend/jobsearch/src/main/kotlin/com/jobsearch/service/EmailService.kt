package com.jobsearch.service

import jakarta.mail.MessagingException
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class EmailService(private val mailSender: JavaMailSender) {
    @Async
    @Throws(MailException::class, MessagingException::class)
    fun sendEmail(to: String, subject: String?, content: String?, emailContent: String?): CompletableFuture<Void> {
        return CompletableFuture.runAsync {
            try {
                val mimeMessage = mailSender.createMimeMessage()
                val helper = MimeMessageHelper(mimeMessage, false, "utf-8")
                helper.setTo(to)
                helper.setSubject(subject!!)
                helper.setText((emailContent ?: content)!!, true)

                mailSender.send(mimeMessage)
                println("Email sent successfully to $to")
            } catch (ex: Exception) {
                println("Failed to send email to " + to + ": " + ex.message)
                throw RuntimeException(ex)
            }
        }
    }
}