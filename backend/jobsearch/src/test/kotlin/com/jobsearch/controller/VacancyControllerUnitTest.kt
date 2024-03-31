package com.jobsearch.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jobsearch.dto.JobFamilyDto
import com.jobsearch.dto.UserResponseDTO
import com.jobsearch.dto.VacancyRequestDTO
import com.jobsearch.dto.VacancyResponseDTO
import com.jobsearch.response.StandardResponse
import com.jobsearch.service.VacancyService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class VacancyControllerUnitTest {
    private lateinit var mockMvc: MockMvc
    @Mock
    private lateinit var vacancyService: VacancyService
    @InjectMocks
    private lateinit var vacancyController: VacancyController

    private val managerUser = UserResponseDTO(
            id = 1,
            firstName= "Mana",
            lastName = "Ger",
            email= "mana@mana",
            roleId = 2,
    )

    private val vacancyResponseDTO = VacancyResponseDTO(
        id = 1,
        name = "Vacancy one",
        salaryExpectation = 10000,
        yearsOfExperience = 3,
        manager = managerUser,
        jobFamily = JobFamilyDto( id= 1, name = "Information Technology"),
        companyName = "Important Company",
        description = "Vacancy one description"
    )
    private val vacancyRequestDTO = VacancyRequestDTO(
        name = "Vacancy one",
        salaryExpectation = 10000,
        yearsOfExperience = 3,
        jobFamilyId = 1,
        companyName = "Important Company",
        description = "Vacancy one description"
    )

    @BeforeEach
    fun setUp() {
        // Open mocks and setup the MockMvc
        MockitoAnnotations.openMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(vacancyController).build()
    }

    @Test
    fun `Should create Vacancy and return status 201`() {
        `when`(vacancyService.createVacancy(vacancyRequestDTO)).thenReturn(vacancyResponseDTO)
        // when
        val requestBody: String = jacksonObjectMapper().writeValueAsString(vacancyRequestDTO)
        // Perform a POST request to create a Vacancy
        mockMvc.post("/api/v1/vacancy") {
            contentType = MediaType.APPLICATION_JSON
            content = requestBody
        }
        // Verify the response
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.data.id") { vacancyResponseDTO.id }
            }
        verify(vacancyService).createVacancy(vacancyRequestDTO)
    }

    @Test
    fun `Should retrieve Vacancy with id 1 and return status 200`() {
        `when`(vacancyService.retrieveVacancy(anyInt())).thenReturn(vacancyResponseDTO)
        val vacancyId = vacancyResponseDTO.id
        // Perform a GET request to retrieve a Vacancy
        mockMvc.get("/api/v1/vacancy/{id}", vacancyId)
        // Validate the response
            .andExpect {
                status { isOk() }
                content { content().contentType(MediaType.APPLICATION_JSON) }
                content { jacksonObjectMapper().writeValueAsString(vacancyResponseDTO) }
            }
        verify(vacancyService).retrieveVacancy(vacancyId)
    }

    @Test
    fun `Should retrieve all Vacancies and return status 200`() {
        `when`(vacancyService.retrieveAllVacancy()).thenReturn(listOf(vacancyResponseDTO))
        // Perform a GET request to retrieve all Vacancies
        mockMvc.get("/api/v1/vacancy")
        // Validate the response
            .andExpect() {
                status { isOk() }
                content { content().contentType(MediaType.APPLICATION_JSON) }
                content { jacksonObjectMapper().writeValueAsString(listOf(vacancyResponseDTO)) }
            }
            .andReturn().response.contentAsString
        verify(vacancyService).retrieveAllVacancy()
    }

    @Test
    fun `Should delete Vacancy with id 1 and return status 204`() {
        doNothing().`when`(vacancyService).deleteVacancy(1)
        // Perform a DELETE request to delete a Vacancy
        val vacancyId = 1
        mockMvc.delete("/api/v1/vacancy/{id}", vacancyId)
            // Validate the response
            .andExpect {
                status { isNoContent() }
            }
        verify(vacancyService).deleteVacancy(vacancyId)
    }

    @Test
    fun `Should update Vacancy with id 1 and return status 200`() {
        val newName = "Vacancy with new name"
        val vacancyResponseDTO = vacancyResponseDTO.copy(name = newName)
        val vacancyRequestDTO = vacancyRequestDTO.copy(name = newName)
        `when`(vacancyService.updateVacancy(1, vacancyRequestDTO)).thenReturn(vacancyResponseDTO)
        // Perform a PUT request to update a Vacancy
        mockMvc.put("/api/v1/vacancy/{id}", 1) {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(vacancyRequestDTO)
        }
            // Validate the response
            .andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
        verify(vacancyService).updateVacancy(1, vacancyRequestDTO)}
}