package com.jobsearch.controller

import com.jobsearch.dto.UserDTO
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
    fun `should create a new user`() {
        val userDTO = UserDTO(null, "John", "Doe", "john@example.com", "password", 1)
        val createdUser = UserDTO(1, "John", "Doe", "john@example.com", "password", 1)

        Mockito.`when`(userService.createUser(userDTO)).thenReturn(createdUser)

        val responseEntity: ResponseEntity<UserDTO> = userController.addUser(userDTO)

        assertEquals(HttpStatus.CREATED, responseEntity.statusCode)
        assertNotNull(responseEntity.body)
        assertEquals(createdUser, responseEntity.body)
    }


    @Test
    fun `should retrieve a user`() {
        val userId = 1
        val retrieveUser = UserDTO(userId, "Saul", "Olguin", "saul@saul.com", "asas", 1)

        Mockito.`when`(userService.retrieveUser(userId)).thenReturn(retrieveUser)

        println("Before calling retrieveUser in UserController")
        val responseEntity: ResponseEntity<UserDTO> = userController.retrieveUser(userId)
        println("After calling retrieveUser in UserController")

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(responseEntity.body)
        assertEquals(retrieveUser, responseEntity.body)
    }

    @Test
    fun `should retrieve all users`() {
        val user1 = UserDTO(1, "Saul", "Olguin", "saul@saul.com", "asas", 1)
        val user2 = UserDTO(2, "Otro", "Usuario", "otro@usuario.com", "pass123", 2)

        Mockito.`when`(userService.retrieveAllUsers()).thenReturn(listOf(user1, user2))

        val responseEntity: ResponseEntity<List<UserDTO>> = userController.retrieveAllUsers()

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
        val updatedUser = UserDTO(userId, "Mario", "Bros", "mario@example.com", "password", 1)

        Mockito.`when`(userService.updateUser(userId, updatedUser)).thenReturn(updatedUser)

        println("Before calling updateUser in UserController")
        val responseEntity : ResponseEntity<UserDTO> = userController.updateUser(userId, updatedUser)
        println("After calling updateUser in UserController")

        assertEquals(HttpStatus.OK, responseEntity.statusCode)
        assertNotNull(responseEntity.body)


        println("Expected User: $updatedUser")
        println("Actual User: ${responseEntity.body}")

        assertEquals(updatedUser, responseEntity.body)
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