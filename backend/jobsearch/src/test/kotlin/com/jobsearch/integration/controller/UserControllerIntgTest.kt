package com.jobsearch.integration.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jobsearch.dto.UserRequestDTO
import com.jobsearch.entity.Role
import com.jobsearch.entity.User

import com.jobsearch.repository.UserRepository

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIntgTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var USER_ENTITY: User
    lateinit var USER_REQUEST: UserRequestDTO
    lateinit var ADMIN_1: User

    companion object {

        val ADMIN_1 = User(
                id = null,
                firstName = "Admin",
                lastName = "Admin",
                password = "test123",
                email = "admin@admin",
                role = Role(3, "admin")
        )


        val USER_ENTITY = User(
                id = null,
                firstName = "Saul",
                lastName = "Olguin",
                password = "test123",
                email = "saul@saul",
                role = Role(2, "candidate")
        )

        val USER_REQUEST =  USER_ENTITY.let {
            UserRequestDTO(
                    id = 1,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    email = it.email,
                    password = it.password,
                    roleId = it.role!!.id
            )
        }
    }


    @BeforeEach
    fun setUp() {
        // Delete all records from the repository
        userRepository.deleteAll()

        // Save the admin mock object
        ADMIN_1 = userRepository.save(UserControllerIntgTest.ADMIN_1)

        // Assign the user entity and user request mock objects
        USER_ENTITY = UserControllerIntgTest.USER_ENTITY
        USER_REQUEST = UserControllerIntgTest.USER_REQUEST


    }

    @Test
    @WithMockUser(authorities = ["admin"])
    fun `should create a new user`() {
        // When creating a new user
        val response = mockMvc.post("/api/v1/users/create") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(USER_REQUEST)
        }

        // Then the user should be created
        response
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.data.id") { isNumber() }
                    jsonPath("$.data.firstName") { value(USER_REQUEST.firstName) }
                    jsonPath("$.data.lastName") { value(USER_REQUEST.lastName) }
                    jsonPath("$.data.email") { value(USER_REQUEST.email) }
                    jsonPath("$.data.role") { value(USER_REQUEST.roleId) }
                }

    }
}