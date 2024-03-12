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
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class VacancyControllerUnitTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @Mock
    private lateinit var vacancyService: VacancyService
    @InjectMocks
    private lateinit var vacancyController: VacancyController

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(vacancyController).build()
    }

    @Test
    fun `Should create Vacancy`() {
        val vacancyResponseDTO = VacancyResponseDTO(
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
        val vacancyRequestDTO = VacancyRequestDTO(
            id = null,
            name = "Vacante 1",
            salaryExpectation = 10000,
            yearsOfExperience = 3,
            jobFamilyId = 1,
            companyName = "Important Company",
            description = "BLABLABLA"
        )

        `when`(vacancyService.createVacancy(vacancyRequestDTO)).thenReturn(vacancyResponseDTO)

        val bodyAsString = mockMvc.post("/api/v1/vacancy") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(vacancyRequestDTO)
        } .andExpect {
            status { isCreated() }
            content {
                contentType(MediaType.APPLICATION_JSON)
            }
        }
            .andReturn().response.contentAsString
        val finalVacancyResponseDTO = jacksonObjectMapper().readValue(bodyAsString, VacancyResponseDTO::class.java)
        Assertions.assertEquals(finalVacancyResponseDTO, vacancyResponseDTO)
    }


    @Test
    fun `Should retrieve Vacancy`() {
        val vacancyId = 1
        val vacancyResponseDTO = VacancyResponseDTO(
            id = vacancyId,
            name = "Vacante 1",
            salaryExpectation = 10000,
            yearsOfExperience = 3,
            managerId = 1,
            jobFamilyId = 1,
            jobFamilyName = "Developer",
            companyName = "Important Company",
            description = "BLABLABLA"
        )

        `when`(vacancyService.retrieveVacancy(anyInt())).thenReturn(vacancyResponseDTO)

        mockMvc.get("/api/v1/vacancy/{id}", vacancyId)
            .andExpect() {
                status { isOk() }
                content { content().contentType(MediaType.APPLICATION_JSON) }
                content { jacksonObjectMapper().writeValueAsString(vacancyResponseDTO) }
            }
    }
}