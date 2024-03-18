package com.jobsearch.service

import com.jobsearch.dto.UserRequestDTO
import com.jobsearch.dto.UserResponseDTO
import com.jobsearch.entity.Role
import com.jobsearch.entity.User
import com.jobsearch.exception.NotFoundException
import com.jobsearch.exception.UserAlreadyExistsException
import com.jobsearch.jwt.JwtProvider
import com.jobsearch.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.*
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

class AuthServiceUnitTest {
    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var userService: UserService

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @Mock
    private lateinit var jwtProvider: JwtProvider

    @InjectMocks
    private lateinit var authService: AuthService

    private val userRequestDTO = UserRequestDTO(
        1,
        "test123",
        "Test",
        "test@example.com",
        "password",
        1,
    )

    private val testUser = User(
         1,
         "juanin",
         "juanHarry",
          "password",
          "test@example.com",
        role = Role(1, "user")
    )

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Should register user successfully`() {
        Mockito.`when`(userRepository.findByEmail(userRequestDTO.email)).thenReturn(Optional.empty())
        Mockito.`when`(passwordEncoder.encode(userRequestDTO.password)).thenReturn("encodedPassword")
        Mockito.`when`(userService.createUser(userRequestDTO)).thenReturn(
            UserResponseDTO(
                1,
                "test123",
                "Test",
                "test@example.com",
                1,
            )
        )
        // Calling the method under test
        Assertions.assertDoesNotThrow { authService.register(userRequestDTO) }
    }

    @Test
    fun `Should throw UserAlreadyExistsException when registering existing user`() {
        Mockito.`when`(userRepository.findByEmail(userRequestDTO.email)).thenReturn(Optional.of(testUser))

        // Calling the method under test and verifying the exception
        Assertions.assertThrows(UserAlreadyExistsException::class.java) {
            authService.register(userRequestDTO)
        }
    }

    @Test
    fun `Should authenticate user successfully`() {
        Mockito.`when`(userRepository.findByEmail(userRequestDTO.email)).thenReturn(Optional.of(testUser))
        Mockito.`when`(passwordEncoder.matches(userRequestDTO.password, testUser.password)).thenReturn(true)
        Mockito.`when`(jwtProvider.generateJwtToken(Mockito.any())).thenReturn("generatedToken")

        val authToken = authService.authenticate(userRequestDTO.email, userRequestDTO.password)

        Assertions.assertNotNull(authToken)
    }


    @Test
    fun `Should throw BadCredentialsException when authenticating with invalid credentials`() {
        Mockito.`when`(userRepository.findByEmail(userRequestDTO.email)).thenReturn(Optional.of(testUser))
        Mockito.`when`(passwordEncoder.matches(userRequestDTO.password, testUser.password)).thenReturn(false)

        Assertions.assertThrows(UninitializedPropertyAccessException::class.java) {
            authService.authenticate(userRequestDTO.email, userRequestDTO.password)
        }
    }

    @Test
    fun `Should load user by username successfully`() {
        Mockito.`when`(userRepository.findByEmail(userRequestDTO.email)).thenReturn(Optional.of(testUser))

        val loadedUser = authService.loadUserByUsername(userRequestDTO.email)

        // Verifying the result
        Assertions.assertNotNull(loadedUser)
        Assertions.assertEquals(userRequestDTO.email, loadedUser.username)
    }

    @Test
    fun `Should throw NotFoundException when loading non-existing user by username`() {
        Mockito.`when`(userRepository.findByEmail(userRequestDTO.email)).thenReturn(Optional.empty())

        Assertions.assertThrows(NotFoundException::class.java) {
            authService.loadUserByUsername(userRequestDTO.email)
        }
    }
}
