package com.jobsearch.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jobsearch.dto.VacancyRequestDTO
import com.jobsearch.dto.VacancyResponseDTO
import com.jobsearch.response.StandardResponse
import com.jobsearch.service.VacancyService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class VacancyControllerUnitTest {
    private lateinit var mockMvc: MockMvc
    @Mock
    private lateinit var vacancyService: VacancyService
    @InjectMocks
    private lateinit var vacancyController: VacancyController


    // Will be initialized in setUp
    lateinit var vacancyResponseDTO: VacancyResponseDTO
    lateinit var vacancyRequestDTO: VacancyRequestDTO


    @BeforeEach
    fun setUp() {
        // Set up the vacancy response DTO
        vacancyResponseDTO = VacancyResponseDTO(
            id = 1,
            name = "Vacancy one",
            salaryExpectation = 10000,
            yearsOfExperience = 3,
            managerId = 1,
            jobFamilyId = 1,
            jobFamilyName = "Information Technology",
            companyName = "Important Company",
            description = "Vacancy one description"
        )

        // Set up the vacancy request DTO
        vacancyRequestDTO = VacancyRequestDTO(
            id = null,
            name = "Vacancy one",
            salaryExpectation = 10000,
            yearsOfExperience = 3,
            jobFamilyId = 1,
            companyName = "Important Company",
            description = "Vacancy one description"
        )

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

        // Verify that the service method to create Vacancy is called just once
        verify(vacancyService, times(1)).createVacancy(vacancyRequestDTO)
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

        // Verify that the service method to retrieve a Vacancy is called just once
        verify(vacancyService, times(1)).retrieveVacancy(anyInt())
    }

    @Test
    fun `Should retrieve all Vacancies and return status 200`() {
        `when`(vacancyService.retrieveAllVacancy()).thenReturn(listOf(vacancyResponseDTO))
        // Perform a GET request to retrieve all Vacancies
        val bodyAsString = mockMvc.get("/api/v1/vacancy")
        // Validate the response
            .andExpect() {
                status { isOk() }
                content { content().contentType(MediaType.APPLICATION_JSON) }
                content { jacksonObjectMapper().writeValueAsString(listOf(vacancyResponseDTO)) }
            }
            .andReturn().response.contentAsString

        // Verify that the service method to retrieve all Vacancies is called just once
        verify(vacancyService, times(1)).retrieveAllVacancy()
    }

    @Test
    fun `Should delete Vacancy with id 1 and return status 204`() {
        doNothing().`when`(vacancyService).deleteVacancy(1)
        // Perform a DELETE request to delete a Vacancy
        val vacancyId = 1
        mockMvc.delete("/api/v1/vacancy/{id}", vacancyId)
            .andExpect {
                status { isNoContent() }
            }
    }

    @Test
    fun `Should update Vacancy with id 1 and return status 200`() {
        vacancyResponseDTO = vacancyResponseDTO.copy(name = "Vacancy two")
        vacancyRequestDTO = vacancyRequestDTO.copy(name = "Vacancy two")
        `when`(vacancyService.updateVacancy(1, vacancyRequestDTO)).thenReturn(vacancyResponseDTO)

        // Perform a PUT request to update a Vacancy
        val bodyAsString = mockMvc.put("/api/v1/vacancy/{id}", 1) {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(vacancyRequestDTO)
        }

            // Validate the response
            .andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }

        // Verify that the service method to update a Vacancy is called just once
        verify(vacancyService, times(1)).updateVacancy(1, vacancyRequestDTO)}
}