package com.jobsearch.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jobsearch.dto.VacancyRequestDTO
import com.jobsearch.dto.VacancyResponseDTO
import com.jobsearch.service.VacancyService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class VacancyControllerUnitTest {
    private lateinit var mockMvc: MockMvc
    @Mock
    private lateinit var vacancyService: VacancyService
    @InjectMocks
    private lateinit var vacancyController: VacancyController

    lateinit var vacancyResponseDTO: VacancyResponseDTO
    lateinit var vacancyRequestDTO: VacancyRequestDTO

    @BeforeEach
    fun setUp() {
        vacancyResponseDTO = VacancyResponseDTO(
            id = 1,
            name = "Vacante 1",
            salaryExpectation = 10000,
            yearsOfExperience = 3,
            managerId = 1,
            jobFamilyId = 1,
            jobFamilyName = "Developer",
            companyName = "Important Company",
            description = "BLABLABLA"
        )
        vacancyRequestDTO = VacancyRequestDTO(
            id = null,
            name = "Vacante 1",
            salaryExpectation = 10000,
            yearsOfExperience = 3,
            jobFamilyId = 1,
            companyName = "Important Company",
            description = "BLABLABLA"
        )
        MockitoAnnotations.openMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(vacancyController).build()
    }

    @Test
    fun `Should create Vacancy and return status 201`() {
        `when`(vacancyService.createVacancy(vacancyRequestDTO)).thenReturn(vacancyResponseDTO)

        val requestBody = jacksonObjectMapper().writeValueAsString(vacancyRequestDTO)

        val bodyAsString = mockMvc.post("/api/v1/vacancy") {
            contentType = MediaType.APPLICATION_JSON
            content = requestBody
        } .andExpect {
            status { isCreated() }
            content {
                contentType(MediaType.APPLICATION_JSON)
            }
        }
            .andReturn().response.contentAsString
        val finalVacancyResponseDTO = jacksonObjectMapper().readValue(bodyAsString, VacancyResponseDTO::class.java)

        Assertions.assertEquals(finalVacancyResponseDTO.id, vacancyResponseDTO.id)
    }


    @Test
    fun `Should retrieve Vacancy with id 1 and return status 200`() {
        val vacancyId = vacancyResponseDTO.id

        `when`(vacancyService.retrieveVacancy(anyInt())).thenReturn(vacancyResponseDTO)

        mockMvc.get("/api/v1/vacancy/{id}", vacancyId)
            .andExpect() {
                status { isOk() }
                content { content().contentType(MediaType.APPLICATION_JSON) }
                content { jacksonObjectMapper().writeValueAsString(vacancyResponseDTO) }
            }
    }

    @Test
    fun `Should retrieve all Vacancies and return status 200`() {
        `when`(vacancyService.retrieveAllVacancy()).thenReturn(listOf(vacancyResponseDTO))

        val bodyAsString = mockMvc.get("/api/v1/vacancy")
            .andExpect() {
                status { isOk() }
                content { content().contentType(MediaType.APPLICATION_JSON) }
                content { jacksonObjectMapper().writeValueAsString(listOf(vacancyResponseDTO)) }
            }
            .andReturn().response.contentAsString

        val expectedBody = jacksonObjectMapper().writeValueAsString(listOf(vacancyResponseDTO))
        Assertions.assertEquals(expectedBody, bodyAsString)
    }

    @Test
    fun `Should delete Vacancy with id 1 and return status 204`() {
        doNothing().`when`(vacancyService).deleteVacancy(1)
        val vacancyId = 1
        mockMvc.delete("/api/v1/vacancy/{id}", vacancyId)
            .andExpect {
                status { isNoContent() }
            }
    }

    @Test
    fun `Should update Vacancy with id 1 and return status 200`() {
        vacancyResponseDTO.name = "Vacante 2"
        vacancyRequestDTO.name = "Vacante 2"

        `when`(vacancyService.updateVacancy(1, vacancyRequestDTO)).thenReturn(vacancyResponseDTO)
        val bodyAsString = mockMvc.put("/api/v1/vacancy/{id}", 1) {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(vacancyRequestDTO)
        } .andExpect {
            status { isOk() }
            content {
                contentType(MediaType.APPLICATION_JSON)
            }
        }
            .andReturn().response.contentAsString
        val finalVacancyResponseDTO = jacksonObjectMapper().readValue(bodyAsString, VacancyResponseDTO::class.java)
        Assertions.assertEquals(finalVacancyResponseDTO.name, vacancyRequestDTO.name)
    }
}