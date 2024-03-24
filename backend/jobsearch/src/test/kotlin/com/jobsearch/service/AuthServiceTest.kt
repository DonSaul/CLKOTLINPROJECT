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

    companion object {
        const val USERNAME = "Thorin"
        const val PASSWORD = "password"
        const val FIRST_NAME = "Thorin"
        const val LAST_NAME = "OakShield"
        const val ENCODED_PASSWORD = "encodedPassword"
        const val MOCKED_TOKEN = "mockedToken"
    }

    @Test
    fun testAuthenticate_ValidCredentials_ReturnsToken() {
        val userDetails = UserDetailsImpl.build(User(email = USERNAME, firstName = FIRST_NAME, lastName = LAST_NAME, password = ENCODED_PASSWORD))
        val user = User(email = USERNAME, firstName = FIRST_NAME, lastName = LAST_NAME, password = ENCODED_PASSWORD)

        Mockito.`when`(userRepository.findByEmail(USERNAME)).thenReturn(Optional.of(user))
        Mockito.`when`(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).thenReturn(true)
        Mockito.`when`(jwtProvider.generateJwtToken(userDetails)).thenReturn(MOCKED_TOKEN)

        val token = authService.authenticate(USERNAME, PASSWORD)
        assertEquals(MOCKED_TOKEN, token)

        Mockito.verify(userRepository).findByEmail(USERNAME)
        Mockito.verify(passwordEncoder).matches(PASSWORD, ENCODED_PASSWORD)
        Mockito.verify(jwtProvider).generateJwtToken(userDetails)
    }

    @Test
    fun testAuthenticate_InvalidCredentials_ThrowsBadCredentialsException() {
        val userDetails = UserDetailsImpl.build(User(email = USERNAME, firstName = FIRST_NAME, lastName = LAST_NAME, password = ENCODED_PASSWORD))
        val user = User(email = USERNAME, firstName = FIRST_NAME, lastName = LAST_NAME, password = ENCODED_PASSWORD)

        Mockito.`when`(userRepository.findByEmail(USERNAME)).thenReturn(Optional.of(user))
        Mockito.`when`(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).thenReturn(false)

        assertThrows<BadCredentialsException> {
            authService.authenticate(USERNAME, PASSWORD)
        }
    }

    @Test
    fun testAuthenticate_UserNotFound_ThrowsUsernameNotFoundException() {
        Mockito.`when`(userRepository.findByEmail(USERNAME)).thenReturn(Optional.empty())

        assertThrows<UsernameNotFoundException> {
            authService.authenticate(USERNAME, PASSWORD)
        }
    }
}
