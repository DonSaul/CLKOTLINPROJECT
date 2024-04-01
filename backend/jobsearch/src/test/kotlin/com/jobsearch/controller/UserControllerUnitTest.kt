package com.jobsearch.controller


import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jobsearch.dto.UserRequestDTO
import com.jobsearch.dto.UserResponseDTO
import com.jobsearch.entity.Role
import com.jobsearch.entity.User
import com.jobsearch.service.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders


class UserControllerUnitTest {

    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var userService: UserService

    @InjectMocks
    private lateinit var userController: UserController

    private val admin = User(
        id = null,
        firstName = "Admin",
        lastName = "Admin",
        password = "test123",
        email = "admin@admin",
        role = Role(3, "admin")
    )

    private val userRequest = UserRequestDTO(
        id = null,
        firstName = "John",
        lastName = "Doe",
        email = "john@example.com",
        password = "test123",
        roleId = 1
    )

    private val userResponse = UserResponseDTO(
        id = 1,
        firstName = "John",
        lastName = "Doe",
        email = "john@example.com",
        roleId = 1
    )

    private val user3 = UserResponseDTO(
        id = 2,
        firstName = "Saul",
        lastName = "Hudson",
        email = "saul@hudson",
        roleId = 1
    )

    private val manager = UserResponseDTO(
        id = 3,
        firstName = "Manager",
        lastName = "Manager",
        email = "manager@manager",
        roleId = 2
    )


    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build()

    }

    @Test
    @WithMockUser(username = "admin@admin", authorities = ["admin"])
    fun `should create a new user`() {
        `when`(userService.createUser(userRequest)).thenReturn(userResponse)

        //val requestBody = jacksonObjectMapper().writeValueAsString(user1)

        mockMvc.post("/api/v1/users/create") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(userRequest)
        }
            .andDo { print() }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
        verify(userService).createUser(userRequest)
    }


    @Test
    fun `should retrieve a user`() {
        `when`(userService.retrieveUser(anyInt())).thenReturn(userResponse)
        val userId = userResponse.id

        mockMvc.get("/api/v1/users/{id}", userId)
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                content { jacksonObjectMapper().writeValueAsString(userResponse) }
            }
        verify(userService).retrieveUser(userId)
    }

    @Test
    fun `should retrieve all users`() {
        `when`(userService.retrieveAllUsers()).thenReturn(listOf(userResponse, user3))

        mockMvc.get("/api/v1/users/all")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                content { jacksonObjectMapper().writeValueAsString(listOf(userResponse, user3)) }
            }
            .andReturn().response.contentAsString
        verify(userService).retrieveAllUsers()
    }


    @Test
    fun `should update a user`() {
        val updateName = "Rollo"
        val userResponse = userResponse.copy(firstName = updateName)
        val userRequest = userRequest.copy(firstName = updateName)

        `when`(userService.updateUser(1, userRequest)).thenReturn(userResponse)

        mockMvc.put("/api/v1/users/{id}", 1) {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(userRequest)
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
        verify(userService).updateUser(1, userRequest)

    }


    @Test
    fun `should delete a user`() {
        `when`(userService.deleteUser(1)).thenReturn("User deleted successfully")
        val userId = 1

        mockMvc.delete("/api/v1/users/{id}", userId)
            .andDo { print() }
            .andExpect {
                status { isOk() }
            }
        verify(userService).deleteUser(userId)

    }
}