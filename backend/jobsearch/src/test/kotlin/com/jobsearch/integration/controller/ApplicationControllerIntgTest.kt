package com.jobsearch.integration.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jobsearch.dto.ApplicationDTO
import com.jobsearch.entity.*
import com.jobsearch.repository.ApplicationRepository
import com.jobsearch.repository.CvRepository
import com.jobsearch.repository.UserRepository
import com.jobsearch.repository.VacancyRepository
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
@ActiveProfiles("test") // Use the "test" profile for testing with H2 database
class ApplicationControllerIntgTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var vacancyRepository: VacancyRepository
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var cvRepository: CvRepository
    @Autowired
    lateinit var applicationRepository: ApplicationRepository
    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var manager1: User
    lateinit var candidate1: User
    lateinit var vacancy1: Vacancy
    lateinit var APPLICATION_REQUEST: ApplicationDTO
    companion object {
        private val candidateRole = Role(1, "candidate")
        private val managerRole = Role(2, "manager")
        private val JOB_FAMILY = JobFamily(1, "Information Technology")

        val CANDIDATE_1 = User(
            id = null,
            firstName = "Cand",
            lastName = "Ide",
            email = "candidate1@mail.com",
            password = "test123",
            role = candidateRole
        )
        val MANAGER_1 = User(
            id = null,
            firstName = "Mana",
            lastName = "Ger",
            email = "manager1@mail.com",
            password = "test123",
            role = managerRole
        )
        val CV_1 = Cv(
            id = null,
            summary = "My Cv summary",
            yearsOfExperience = 3,
            salaryExpectation = 3000,
            education = "School",
            user = CANDIDATE_1
        )
        val VACANCY_ENTITY = Vacancy(
            id = null,
            name = "Vacancy one",
            companyName = "Important Company",
            salaryExpectation = 10000,
            yearsOfExperience = 3,
            description = "Vacancy one description",
            jobFamily = JOB_FAMILY,
            manager = MANAGER_1
        )
    }
    @BeforeEach
    fun setUp() {
        // Delete all records from the repository
        applicationRepository.deleteAll()
        vacancyRepository.deleteAll()
        cvRepository.deleteAll()
        userRepository.deleteAll()
        // Save the managers and candidate mock objects
        manager1 = userRepository.save(MANAGER_1)
        candidate1 = userRepository.save(CANDIDATE_1)
        vacancy1 = vacancyRepository.save(VACANCY_ENTITY)
        cvRepository.save(CV_1)
        APPLICATION_REQUEST = ApplicationDTO(vacancyId = vacancy1.id!!)
    }

    @Test
    @WithMockUser(username = "candidate1@mail.com", authorities = ["candidate"])
    fun `Should create application if user is candidate`() {
        // Create a POST request to the "/api/v1/application" endpoint
        val response = mockMvc.post("/api/v1/application") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(APPLICATION_REQUEST)
        }
        // Then
        response
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.applicationId") { isNumber() }
            }
    }

    @Test
    @WithMockUser(username = "candidate1@mail.com", authorities = ["candidate"])
    fun `Should not create application if user already applied`() {
        val firstApplication = mockMvc.post("/api/v1/application") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(APPLICATION_REQUEST)
        }
        // Create a POST request to the "/api/v1/application" endpoint
        val response = mockMvc.post("/api/v1/application") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(APPLICATION_REQUEST)
        }
        // Then
        response
            .andExpect {
                status { isForbidden() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

}