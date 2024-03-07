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
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@ExtendWith(MockitoExtension::class)
class AuthControllerTest {
    @InjectMocks
    lateinit var authController: AuthController

    @Mock
    lateinit var authService: AuthService

    companion object {
        const val DUMMY_TOKEN = "dummy_token"
        const val USERNAME = "ThorinOakShield"
        const val PASSWORD = "password"
        const val FIRST_NAME = "Thorin"
        const val LAST_NAME = "OakShield"
        const val ENCODED_PASSWORD = "encodedPassword"
    }

    @Test
    fun `should register user`() {
        val userDTO = UserDTO(12121321, FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, 12)
        val result = authController.register(userDTO)
        assertEquals(HttpStatus.CREATED, result.statusCode)
        Mockito.verify(authService).register(userDTO)
    }

    @Test
    fun `should authenticate user`() {
        val loginRequest = LoginRequest(USERNAME, PASSWORD)
        Mockito.`when`(authService.authenticate(loginRequest.username, loginRequest.password)).thenReturn(DUMMY_TOKEN)
        val result = authController.authenticateUser(loginRequest) as ResponseEntity<*>
        assertEquals(HttpStatus.OK, result.statusCode)
        assertTrue(result.body is JwtResponse)
        assertEquals(DUMMY_TOKEN, (result.body as JwtResponse).token)
        Mockito.verify(authService).authenticate(loginRequest.username, loginRequest.password)
    }
}
