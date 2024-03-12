package com.jobsearch.controller

import com.jobsearch.service.RecoverPasswordService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}