package com.jobsearch.service

import com.jobsearch.entity.User
import com.jobsearch.jwt.JwtProvider
import com.jobsearch.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@ExtendWith(MockitoExtension::class)
class AuthServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @Mock
    private lateinit var jwtProvider: JwtProvider

    @InjectMocks
    private lateinit var authService: AuthService

    @Test
    fun testAuthenticate_ValidCredentials_ReturnsToken() {
        val username = "Thorin"
        val password = "password"
        val firstName = "Thorin"
        val lastname = "OakShield"
        val encodedPassword = "encodedPassword"
        val userDetails = UserDetailsImpl.build(User(email = username, firstName = "Thorin", lastName = "OakShield", password = encodedPassword))

        val user = User(email = username, firstName = firstName, lastName = lastname ,password = encodedPassword)
        Mockito.`when`(userRepository.findByEmail(username)).thenReturn(Optional.of(user))

        Mockito.`when`(passwordEncoder.matches(password, encodedPassword)).thenReturn(true)

        Mockito.`when`(jwtProvider.generateJwtToken(userDetails)).thenReturn("mockedToken")

        val token = authService.authenticate(username, password)

        assertEquals("mockedToken", token)
    }

    @Test
    fun testAuthenticate_InvalidCredentials_ThrowsBadCredentialsException() {
        val username = "test@example.com"
        val password = "password"
        val firstname = "Thorin"
        val lastname = "OakShield"
        val encodedPassword = "encodedPassword"
        val userDetails = UserDetailsImpl.build(User(email = username, firstName = "Thorin", lastName = "OakShield", password = encodedPassword))

        val user = User(email = username, firstName = firstname, lastName = lastname,password = encodedPassword)
        Mockito.`when`(userRepository.findByEmail(username)).thenReturn(Optional.of(user))

        Mockito.`when`(passwordEncoder.matches(password, encodedPassword)).thenReturn(false)

        assertThrows<BadCredentialsException> { authService.authenticate(username, password) }
    }

    @Test
    fun testAuthenticate_UserNotFound_ThrowsUsernameNotFoundException() {
        val username = "test@example.com"
        val password = "password"

        Mockito.`when`(userRepository.findByEmail(username)).thenReturn(Optional.empty())

        assertThrows<UsernameNotFoundException> { authService.authenticate(username, password) }
    }
}
