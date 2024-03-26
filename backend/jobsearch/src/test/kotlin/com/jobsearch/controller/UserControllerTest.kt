package com.jobsearch.controller


import com.jobsearch.dto.UserRequestDTO
import com.jobsearch.dto.UserResponseDTO
import com.jobsearch.service.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class UserControllerTest {

    @MockBean
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userController: UserController

    @Test
    @WithMockUser(authorities = ["admin"])
    fun `should create a new user`() {
        val userRequestDTO = UserRequestDTO(null, "John", "Doe", "john@example.com", "password", 1)
        val createdUser = UserResponseDTO(1, "John", "Doe", "john@example.com", 1)

        Mockito.`when`(userService.createUser(userRequestDTO)).thenReturn(createdUser)

        val responseEntity: ResponseEntity<UserResponseDTO> = userController.addUser(userRequestDTO)

        assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
        assertNotNull(responseEntity.body)
        assertEquals(createdUser, responseEntity.body)
    }


    @Test
    fun `should retrieve a user`() {
        val userId = 1
        val retrieveUser = UserResponseDTO(userId, "Saul", "Olguin", "saul@saul.com",  1)

        Mockito.`when`(userService.retrieveUser(userId)).thenReturn(retrieveUser)

        println("Before calling retrieveUser in UserController")
        val responseEntity: ResponseEntity<UserResponseDTO> = userController.retrieveUser(userId)
        println("After calling retrieveUser in UserController")

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(responseEntity.body)
        assertEquals(retrieveUser, responseEntity.body)
    }

    @Test
    fun `should retrieve all users`() {
        val user1 = UserResponseDTO(1, "Saul", "Olguin", "saul@saul.com",  1)
        val user2 = UserResponseDTO(2, "Otro", "Usuario", "otro@usuario.com",  2)

        Mockito.`when`(userService.retrieveAllUsers()).thenReturn(listOf(user1, user2))

        val responseEntity: ResponseEntity<List<UserResponseDTO>> = userController.retrieveAllUsers()

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(responseEntity.body)

        val retrievedUsers = responseEntity.body
        assertEquals(2, retrievedUsers?.size)

        assertTrue(retrievedUsers?.contains(user1) ?: false)
        assertTrue(retrievedUsers?.contains(user2) ?: false)
    }


    @Test
    fun `should update a user`() {
        val userId = 1
        val updatedUser = UserRequestDTO(userId, "Mario", "Bros", "mario@example.com",  "password", 1)
        val updatedUserResponse = UserResponseDTO(userId, "Mario", "Brother", "marioBros@example.com", 1)

        Mockito.`when`(userService.updateUser(userId, updatedUser)).thenReturn(updatedUserResponse)

        println("Before calling updateUser in UserController")
        val responseEntity : ResponseEntity<UserResponseDTO> = userController.updateUser(userId, updatedUser)
        println("After calling updateUser in UserController")

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(responseEntity.body)

        println("Expected User: $updatedUserResponse")
        println("Actual User: ${responseEntity.body}")

        assertEquals(updatedUserResponse, responseEntity.body)
    }


    @Test
    fun `should delete a user`() {
        val userId = 1
        val deleteUser = "User with ID $userId deleted successfully"

        Mockito.`when`(userService.deleteUser(userId)).thenReturn(deleteUser)

        val responseEntity: ResponseEntity<String> = userController.deleteUser(userId)

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(responseEntity.body)
        assertEquals(deleteUser, responseEntity.body)
    }

    @Test
    fun `should updateUserNotificationActivated`(){

    }
}