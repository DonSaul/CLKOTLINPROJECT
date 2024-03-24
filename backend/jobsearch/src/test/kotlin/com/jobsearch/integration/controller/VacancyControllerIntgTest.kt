package com.jobsearch.integration.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jobsearch.dto.VacancyRequestDTO
import com.jobsearch.entity.JobFamily
import com.jobsearch.entity.Role
import com.jobsearch.entity.User
import com.jobsearch.entity.Vacancy
import com.jobsearch.repository.UserRepository
import com.jobsearch.repository.VacancyRepository
import jakarta.transaction.Transactional
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.*
/**
 * Integration test for VacancyController.
 * Uses H2 database for testing with hardcoded base data contained in data.sql file.
 * Base data for testing is created in setUp method.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test") // Use the "test" profile for testing with H2 database
class VacancyControllerIntgTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var vacancyRepository: VacancyRepository
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var objectMapper: ObjectMapper

    // Will be initialized in setUp
    lateinit var MANAGER_1: User
    lateinit var MANAGER_2: User
    lateinit var CANDIDATE_1: User
    lateinit var VACANCY_ENTITY: Vacancy
    lateinit var VACANCY_REQUEST: VacancyRequestDTO
    lateinit var JOB_FAMILY: JobFamily

    companion object {
        // Mock objects, will be initialized in setUp
        val JOB_FAMILY = JobFamily(1, "Information Technology")
        val MANAGER_1 = User(
            id = null,
            firstName = "Mana",
            lastName = "Ger",
            email = "manager1@mail.com",
            password = "test123",
            role = Role(1,"manager")
        )
        val MANAGER_2 = User(
            id = null,
            firstName = "Mana2",
            lastName = "Ger2",
            email = "manager2@mail.com",
            password = "test123",
            role = Role(1,"manager")
        )
        val CANDIDATE_1 = User(
            id = null,
            firstName = "Cand",
            lastName = "Ide",
            email = "candidate1@mail.com",
            password = "test123",
            role = Role(2,"candidate")
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
        val VACANCY_REQUEST = VACANCY_ENTITY.let{
            VacancyRequestDTO(
                id = null,
                name = it.name,
                companyName = it.companyName,
                salaryExpectation = it.salaryExpectation,
                yearsOfExperience = it.yearsOfExperience,
                description = it.description,
                jobFamilyId = it.jobFamily.id!!
            )
        }
    }

    @BeforeEach
    fun setUp() {
        // Delete all records from the repository
        vacancyRepository.deleteAll()
        userRepository.deleteAll()

        // Save the managers and candidate mock objects
        MANAGER_1 = userRepository.save(VacancyControllerIntgTest.MANAGER_1)
        MANAGER_2 = userRepository.save(VacancyControllerIntgTest.MANAGER_2)
        CANDIDATE_1 = userRepository.save(VacancyControllerIntgTest.CANDIDATE_1)

        // Assign the vacancy entity, vacancy request and job family mock objects
        VACANCY_ENTITY = VacancyControllerIntgTest.VACANCY_ENTITY
        VACANCY_REQUEST = VacancyControllerIntgTest.VACANCY_REQUEST
        JOB_FAMILY = VacancyControllerIntgTest.JOB_FAMILY
    }

    @Test
    @WithMockUser(username = "manager1@mail.com", authorities = ["manager"])
    fun `Should create vacancy if user has manager role`() {
        // Create a POST request to the "/api/v1/vacancy" endpoint
        val response = mockMvc.post("/api/v1/vacancy") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(VACANCY_REQUEST)
        }

        // Then
        response
            .andDo { print() }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.data.id") { isNumber() }
                jsonPath("$.data.name") { value(VACANCY_ENTITY.name) }
                jsonPath("$.data.companyName") { value(VACANCY_ENTITY.companyName) }
                jsonPath("$.data.salaryExpectation") { value(VACANCY_ENTITY.salaryExpectation) }
                jsonPath("$.data.yearsOfExperience") { value(VACANCY_ENTITY.yearsOfExperience) }
                jsonPath("$.data.description") { value(VACANCY_ENTITY.description) }
                jsonPath("$.data.jobFamilyId") { value(JOB_FAMILY.id) }
            }
    }

    @Test
    @WithMockUser(username = "user@mail.com", authorities = ["candidate"])
    fun `Should not create vacancy if user is has not the manager role`() {
        // Create a POST request to the "/api/v1/vacancy" endpoint without the manager role
        val response = mockMvc.post("/api/v1/vacancy") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(VACANCY_REQUEST)
        }
        // Then
        response
            .andDo { print() }
            // Check that the response status is forbidden
            .andExpect {
            status { isForbidden() }
        }
    }

    @Test
    @WithMockUser(username = "manager1@mail.com", authorities = ["manager"])
    fun `Should update vacancy if user is the vacancy manager`() {
        // given
        val savedVacancy = vacancyRepository.save(VACANCY_ENTITY)
        // when
        val newName = "Vacancy with new name"
        val vacancyWithNewName = VACANCY_REQUEST.copy(name = newName)
        // Create a PUT request to the "/api/v1/vacancy/{vacancyId}" endpoint with the new name
        val response = mockMvc.put("/api/v1/vacancy/${savedVacancy.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(vacancyWithNewName)
        }
        // then
        response
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                // Check that the response contains the new name
                jsonPath("$.data.name") { value(newName) }
            }
    }

    @Test
    @WithMockUser(username = "user@mail.com", authorities = ["candidate"])
    fun `Should not update vacancy if user has not manager role`() {
        // given
        val savedVacancy = vacancyRepository.save(VACANCY_ENTITY)
        // when
        val newName = "Vacante with new name"
        val vacancy1WithNewName = VACANCY_REQUEST.copy(name = newName)
        // Create a PUT request to the "/api/v1/vacancy/{vacancyId}" endpoint with the new name and the candidate role
        val response = mockMvc.put("/api/v1/vacancy/${savedVacancy.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(vacancy1WithNewName)
        }
        // then
        response
            .andDo { print() }
            // Check that the response status is forbidden
            .andExpect {
            status { isForbidden() }
        }
    }

    @Test
    @WithMockUser(username = "manager2@mail.com", authorities = ["manager"])
    fun `Should not update vacancy if user is not the vacancy manager`() {
        // given
        val savedVacancy = vacancyRepository.save(VACANCY_ENTITY)
        // when
        val newName = "Vacante with new name"
        val vacancy1WithNewName = VACANCY_REQUEST.copy(name = newName)
        // Create a PUT request to the "/api/v1/vacancy/{vacancyId}" endpoint with the new name and the manager2 user
        val response = mockMvc.put("/api/v1/vacancy/${savedVacancy.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(vacancy1WithNewName)
        }
        // then
        response
            .andDo { print() }
            // Check that the response status is forbidden
            .andExpect {
            status { isForbidden() }
        }
    }

    @Test
    @WithMockUser(username = "manager1@mail.com", authorities = ["manager"])
    fun `Should not update vacancy if not found`() {
        // given
        // There is no vacancy with id 100
        val vacancyId = 100
        // when
        // Create a PUT request to the "/api/v1/vacancy/{vacancyId}" endpoint
        val response = mockMvc.put("/api/v1/vacancy/$vacancyId") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(VACANCY_REQUEST)
        }
        // then
        response
            .andDo { print() }
            // Check that the response status is not found
            .andExpect {
            status { isNotFound() }
        }
    }

    @Test
    @WithMockUser(username = "manager1@mail.com", authorities = ["manager"])
    fun `Should delete vacancy when user is the vancy manager`() {
        // given
        val savedVacancy = vacancyRepository.save(VACANCY_ENTITY)
        // when
        // Create a DELETE request to the "/api/v1/vacancy/{vacancyId}" endpoint
        val response = mockMvc.delete("/api/v1/vacancy/${savedVacancy.id}")
        // then
        response
            .andDo { print() }
            // Check that the response status is no content
            .andExpect {
            status { isNoContent() }
        }
    }

    @Test
    @WithMockUser(username = "manager2@mail.com", authorities = ["manager"])
    fun `Should not delete vacancy if user is not the vacancy manager`() {
        // given
        val savedVacancy = vacancyRepository.save(VACANCY_ENTITY)
        // when
        // Create a DELETE request to the "/api/v1/vacancy/{vacancyId}" endpoint with the manager2 user
        val response = mockMvc.delete("/api/v1/vacancy/${savedVacancy.id}")
        // then
        response
            .andDo { print() }
            // Check that the response status is forbidden
            .andExpect {
            status { isForbidden() }
        }
    }

    @Test
    @WithMockUser(username = "manager1@mail.com", authorities = ["manager"])
    fun `Should return 204 when delete and vacancy is not found`() {
        // given
        // There is no vacancy with id 100
        val vacancyId = 100
        // when
        val response = mockMvc.delete("/api/v1/vacancy/$vacancyId")
        // then
        response
            .andDo { print() }
            // Check that the response status is no content
            .andExpect {
            status { isNoContent() }
        }
    }

    @Test
    @WithMockUser(username = "candidate1@mail.com", authorities = ["candidate"])
    fun `Should retrieve vacancy by id`() {
        // given
        val savedVacancy = vacancyRepository.save(VACANCY_ENTITY)
        // when
        val response = mockMvc.get("/api/v1/vacancy/${savedVacancy.id}")
        // then
        response
            .andDo { print() }
            // Check that the response status is ok and body contains the expected data
            .andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.data.name") { value(savedVacancy.name) }
            jsonPath("$.data.companyName") { value(savedVacancy.companyName) }
            jsonPath("$.data.salaryExpectation") { value(savedVacancy.salaryExpectation) }
            jsonPath("$.data.yearsOfExperience") { value(savedVacancy.yearsOfExperience) }
            jsonPath("$.data.description") { value(savedVacancy.description) }
            jsonPath("$.data.jobFamilyId") { value(savedVacancy.jobFamily.id) }
            jsonPath("$.data.jobFamilyName") { value(savedVacancy.jobFamily.name) }
            jsonPath("$.data.managerId") { value(savedVacancy.manager.id) }
        }
    }
    @Test
    @WithMockUser(username = "candidate1@mail.com", authorities = ["candidate"])
    fun `Should return 404 if vacancy is not found`() {
        // given
        // There is no vacancy with id 100
        val vacancyId = 100
        // when
        val response = mockMvc.get("/api/v1/vacancy/$vacancyId")
        // then
        response
            .andDo { print() }
            // Check that the response status is not found
            .andExpect {
            status { isNotFound() }
        }
    }

    @Test
    @WithMockUser(username = "candidate1@mail.com", authorities = ["candidate"])
    fun `Should return list of vacancies`() {
        // given
        val savedVacancy1 = vacancyRepository.save(VACANCY_ENTITY.copy(name = "Vacancy one"))
        val savedVacancy2 = vacancyRepository.save(VACANCY_ENTITY.copy(name = "Vacancy two"))
        val savedVacancy3 = vacancyRepository.save(VACANCY_ENTITY.copy(name = "Vacancy three"))

        // when
        val response = mockMvc.get("/api/v1/vacancy")
        // then
        response
            .andDo { print() }
            .andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.data[0].name") { value(savedVacancy1.name) }
            jsonPath("$.data[1].name") { value(savedVacancy2.name) }
            jsonPath("$.data[2].name") { value(savedVacancy3.name) }
        }
    }

    @Test
    @WithMockUser(username = "manager1@mail.com", authorities = ["manager"])
    fun `Should return just the vacancies created by the manager`() {
        // given
        val savedVacancy1 = vacancyRepository.save(VACANCY_ENTITY.copy(name = "Vacancy one", manager = MANAGER_1))
        val savedVacancy2 = vacancyRepository.save(VACANCY_ENTITY.copy(name = "Vacancy two", manager = MANAGER_1))
        val savedVacancy3 = vacancyRepository.save(VACANCY_ENTITY.copy(name = "Vacancy three", manager = MANAGER_1))
        val savedVacancy4 = vacancyRepository.save(VACANCY_ENTITY.copy(name = "Vacancy four", manager = MANAGER_2))
        // when
        val response = mockMvc.get("/api/v1/vacancy/my-vacancies")
        // then
        response
            .andDo { print() }
            // Check that the response status is ok and body contains the expected data
            .andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.data", hasSize<Int>(3)) // Ensure the response list has a size of 3
        }
    }

    @Test
    @WithMockUser(username = "candidate1@mail.com", authorities = ["candidate"])
    fun `Should return vacancies with salary greater than 5000`() {
        // given
        val savedVacancy1 = vacancyRepository.save(VACANCY_ENTITY.copy(name = "Vacancy one", salaryExpectation = 4000))
        val savedVacancy2 = vacancyRepository.save(VACANCY_ENTITY.copy(name = "Vacancy two", salaryExpectation = 3000))
        val savedVacancy3 = vacancyRepository.save(VACANCY_ENTITY.copy(name = "Vacancy three", salaryExpectation = 8000))
        val savedVacancy4 = vacancyRepository.save(VACANCY_ENTITY.copy(name = "Vacancy four", salaryExpectation = 9000))
        // when
        val response = mockMvc.get("/api/v1/vacancy/search?salary=5000")
        // then
        response
            .andDo { print() }
            .andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.data", hasSize<Int>(2)) // Ensure the response list has a size of 2
        }
    }
}