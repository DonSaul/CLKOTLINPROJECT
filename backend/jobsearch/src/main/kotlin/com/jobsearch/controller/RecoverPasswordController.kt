package com.jobsearch.controller

import com.jobsearch.exception.NotFoundException
import com.jobsearch.service.RecoverPasswordService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/recoverPassword")
class RecoverPasswordController(private val recoverPasswordService: RecoverPasswordService){

    data class ForgotPasswordRequest(
        val email: String
    )
    @PostMapping("/forgotPassword")
    fun forgotPassword(@RequestBody request: ForgotPasswordRequest): ResponseEntity<String> {
        recoverPasswordService.sendRecoverPassword(request.email)
        return ResponseEntity.status(HttpStatus.CREATED).body("Recover Password in process...")
    }

    @PostMapping("/changePassword")
    fun changePassword(@RequestParam token: String, @RequestParam newPassword: String): ResponseEntity<String> {
        try {
            recoverPasswordService.changePassword(token, newPassword)
            return ResponseEntity.status(HttpStatus.CREATED).body("Password updated successfully.")
        } catch (e: NotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        }
    }
}