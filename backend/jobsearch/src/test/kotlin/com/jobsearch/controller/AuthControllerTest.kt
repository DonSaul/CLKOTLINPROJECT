package com.jobsearch.controller

import com.jobsearch.dto.JwtResponse
import com.jobsearch.dto.LoginRequest
import com.jobsearch.dto.UserDTO
import com.jobsearch.service.AuthService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@ExtendWith(MockitoExtension::class)
class AuthControllerTest {

    @InjectMocks
    lateinit var authController: AuthController

    @Mock
    lateinit var authService: AuthService

    @Test
    fun `should register user`() {
        val userDTO = UserDTO(12121321, "Thorin", "OakShield", "ThorinOakShield", "password", 12)
        val result = authController.register(userDTO)
        assertEquals(HttpStatus.CREATED, result.statusCode)
    }

    @Test
    fun `should authenticate user`() {
        val loginRequest = LoginRequest("ThorinOakShield", "password")
        `when`(authService.authenticate(loginRequest.username, loginRequest.password)).thenReturn("dummy_token")
        val result = authController.authenticateUser(loginRequest) as ResponseEntity<*>
        assertEquals(HttpStatus.OK, result.statusCode)
        assertTrue(result.body is JwtResponse)
        assertEquals("dummy_token", (result.body as JwtResponse).token)
    }

}