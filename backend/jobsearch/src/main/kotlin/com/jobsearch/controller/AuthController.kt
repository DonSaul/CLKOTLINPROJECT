package com.jobsearch.controller

import com.jobsearch.dto.JwtResponse
import com.jobsearch.dto.LoginRequest
import com.jobsearch.dto.UserDTO
import com.jobsearch.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@RequestBody userDto: UserDTO): ResponseEntity<Void> {
        authService.register(userDto)
        return ResponseEntity.created(URI.create("/api/v1/auth/register")).build()
    }

    @PostMapping("/login")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
        return try {
            val jwt = authService.authenticate(loginRequest.username, loginRequest.password)
            ResponseEntity.ok(JwtResponse(jwt))
        } catch (ex: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.")
        }
    }
}
