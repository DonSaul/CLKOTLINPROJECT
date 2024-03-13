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
import org.springframework.test.web.servlet.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional // Reverts the changes made to the database after finish
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

    companion object {
        val JOB_FAMILY = JobFamily(1, "Information Technology")
        val MANAGER_1 = User(
            id = 1,
            firstName = "Mana",
            lastName = "Ger",
            email = "manager1@mail.com",
            password = "test123",
            role = Role(1,"manager")
        )
        val MANAGER_2 = User(
            id = 2,
            firstName = "Mana2",
            lastName = "Ger2",
            email = "manager2@mail.com",
            password = "test123",
            role = Role(1,"manager")
        )
        val CANDIDATE_1 = User(
            id = 3,
            firstName = "Cand",
            lastName = "Ide",
            email = "candidate1@mail.com",
            password = "test123",
            role = Role(2,"candidate")
        )
        val VACANCY_ENTITY = Vacancy(
            id = 1,
            name = "Vacante 1",
            companyName = "Important Company",
            salaryExpectation = 10000,
            yearsOfExperience = 3,
            description = "BLABLABLA",
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
        vacancyRepository.deleteAll()
        userRepository.deleteAll()

        MANAGER_1 = userRepository.save(VacancyControllerIntgTest.MANAGER_1)
        MANAGER_2 = userRepository.save(VacancyControllerIntgTest.MANAGER_2)
        CANDIDATE_1 = userRepository.save(VacancyControllerIntgTest.CANDIDATE_1)
        VACANCY_ENTITY = VacancyControllerIntgTest.VACANCY_ENTITY
        VACANCY_REQUEST = VacancyControllerIntgTest.VACANCY_REQUEST
    }

    @Test
    @WithMockUser(username = "manager1@mail.com", roles = ["manager"])
    fun `Should create vacancy and return it`() {
        // when
        val response = mockMvc.post("/api/v1/vacancy"){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(VACANCY_REQUEST)
        }
        // then
        response
            .andDo { print() }
            .andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.id") { isNumber() }
            jsonPath("$.name") { value(VACANCY_ENTITY.name) }
            jsonPath("$.companyName") { value(VACANCY_ENTITY.companyName) }
            jsonPath("$.salaryExpectation") { value(VACANCY_ENTITY.salaryExpectation) }
            jsonPath("$.yearsOfExperience") { value(VACANCY_ENTITY.yearsOfExperience) }
            jsonPath("$.description") { value(VACANCY_ENTITY.description) }
            jsonPath("$.jobFamilyId") { value(JOB_FAMILY.id) }
        }
    }

    @Test
    fun `Should not create vacancy if not manager`() {
        // when
        val response = mockMvc.post("/api/v1/vacancy") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(VACANCY_REQUEST)
        }
        // then
        response
            .andDo { print() }
            .andExpect {
            status { isForbidden() }
        }
    }

    @Test
    @WithMockUser(username = "manager1@mail.com", roles = ["manager"])
    fun `Should update vacancy and return it`() {
        // given
        val savedVacancy = vacancyRepository.save(VACANCY_ENTITY)
        // when
        val newName = "Vacante with new name"
        val vacancy1WithNewName = VACANCY_REQUEST.copy(name = newName)
        val response = mockMvc.put("/api/v1/vacancy/${savedVacancy.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(vacancy1WithNewName)
        }
        // then
        response
            .andDo { print() }
            .andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.name") { value(newName) }
        }
    }

    @Test
    @WithMockUser(username = "user@mail.com", roles = ["candidate"])
    fun `Should not update vacancy if not manager`() {
        // given
        val savedVacancy = vacancyRepository.save(VACANCY_ENTITY)
        // when
        val newName = "Vacante with new name"
        val vacancy1WithNewName = VACANCY_REQUEST.copy(name = newName)
        val response = mockMvc.put("/api/v1/vacancy/${savedVacancy.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(vacancy1WithNewName)
        }
        // then
        response
            .andDo { print() }
            .andExpect {
            status { isForbidden() }
        }
    }

    @Test
    @WithMockUser(username = "manager2@mail.com", roles = ["manager"])
    fun `Should not update vacancy if manager not found`() {
        // given
        val savedVacancy = vacancyRepository.save(VACANCY_ENTITY)
        // when
        val newName = "Vacante with new name"
        val vacancy1WithNewName = VACANCY_REQUEST.copy(name = newName)
        val response = mockMvc.put("/api/v1/vacancy/${savedVacancy.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(vacancy1WithNewName)
        }
        // then
        response
            .andDo { print() }
            .andExpect {
            status { isForbidden() }
        }
    }

    @Test
    @WithMockUser(username = "manager1@mail.com", roles = ["manager"])
    fun `Should not update vacancy if not found`() {
        // given
        // when
        val response = mockMvc.put("/api/v1/vacancy/${VACANCY_ENTITY.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(VACANCY_REQUEST)
        }
        // then
        response
            .andDo { print() }
            .andExpect {
            status { isNotFound() }
        }
    }

    @Test
    @WithMockUser(username = "manager1@mail.com", roles = ["manager"])
    fun `Should delete vacancy`() {
        // given
        val savedVacancy = vacancyRepository.save(VACANCY_ENTITY)
        // when
        val response = mockMvc.delete("/api/v1/vacancy/${savedVacancy.id}")
        // then
        response
            .andDo { print() }
            .andExpect {
            status { isNoContent() }
        }
    }

    @Test
    @WithMockUser(username = "manager2@mail.com", roles = ["manager"])
    fun `Should not delete vacancy if not owner manager`() {
        // given
        val savedVacancy = vacancyRepository.save(VACANCY_ENTITY)
        // when
        val response = mockMvc.delete("/api/v1/vacancy/${savedVacancy.id}")
        // then
        response
            .andDo { print() }
            .andExpect {
            status { isForbidden() }
        }
    }

    @Test
    @WithMockUser(username = "manager1@mail.com", roles = ["manager"])
    fun `Should return 204 if not found`() {
        // given
        // when
        val response = mockMvc.delete("/api/v1/vacancy/${VACANCY_ENTITY.id}")
        // then
        response
            .andDo { print() }
            .andExpect {
            status { isNoContent() }
        }
    }

    @Test
    @WithMockUser(username = "candidate1@mail.com", roles = ["candidate"])
    fun `Should retrieve vacancy by id`() {
        // given
        val savedVacancy = vacancyRepository.save(VACANCY_ENTITY)
        // when
        val response = mockMvc.get("/api/v1/vacancy/${savedVacancy.id}")
        // then
        response
            .andDo { print() }
            .andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.name") { value(VACANCY_ENTITY.name) }
            jsonPath("$.companyName") { value(VACANCY_ENTITY.companyName) }
            jsonPath("$.salaryExpectation") { value(VACANCY_ENTITY.salaryExpectation) }
            jsonPath("$.yearsOfExperience") { value(VACANCY_ENTITY.yearsOfExperience) }
            jsonPath("$.description") { value(VACANCY_ENTITY.description) }
            jsonPath("$.jobFamilyId") { value(VACANCY_ENTITY.jobFamily.id) }
            jsonPath("$.jobFamilyName") { value(VACANCY_ENTITY.jobFamily.name) }
            jsonPath("$.managerId") { value(VACANCY_ENTITY.manager.id) }
        }
    }
    @Test
    @WithMockUser(username = "candidate1@mail.com", roles = ["candidate"])
    fun `Should return 404 if not found`() {
        // given
        // when
        val response = mockMvc.get("/api/v1/vacancy/${VACANCY_ENTITY.id}")
        // then
        response
            .andDo { print() }
            .andExpect {
            status { isNotFound() }
        }
    }

    @Test
    @WithMockUser(username = "candidate1@mail.com", roles = ["candidate"])
    fun `Should return list of vacancies`() {
        // given
        val savedVacancy1 = vacancyRepository.save(VACANCY_ENTITY)
        val savedVacancy2 = vacancyRepository.save(VACANCY_ENTITY.copy(id = 2, name = "Vacante 2"))
        val savedVacancy3 = vacancyRepository.save(VACANCY_ENTITY.copy(id = 3, name = "Vacante 3"))

        // when
        val response = mockMvc.get("/api/v1/vacancy")
        // then
        response
            .andDo { print() }
            .andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$[0].name") { value(savedVacancy1.name) }
            jsonPath("$[1].name") { value(savedVacancy2.name) }
            jsonPath("$[2].name") { value(savedVacancy3.name) }
        }
    }

    @Test
    @WithMockUser(username = "manager1@mail.com", roles = ["manager"])
    fun `Should return just the manager vacancies`() {
        // given
        val savedVacancy1 = vacancyRepository.save(VACANCY_ENTITY)
        val savedVacancy2 = vacancyRepository.save(VACANCY_ENTITY.copy(id = 2, name = "Vacante 2"))
        val savedVacancy3 = vacancyRepository.save(VACANCY_ENTITY.copy(id = 3, name = "Vacante 3"))
        val savedVacancy4 = vacancyRepository.save(VACANCY_ENTITY.copy(id = 4, name = "Vacante 4", manager = MANAGER_2))
        // when
        val response = mockMvc.get("/api/v1/vacancy/my-vacancies")
        // then
        response
            .andDo { print() }
            .andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$", hasSize<Int>(3)) // Ensure the response list has a size of 3
        }
    }

    @Test
    @WithMockUser(username = "candidate1@mail.com", roles = ["candite"])
    fun `Should return vacancies with salary greater than 5000`() {
        // given
        val savedVacancy1 = vacancyRepository.save(VACANCY_ENTITY.copy(id = 1, name = "Vacante 1", salaryExpectation = 4000))
        val savedVacancy2 = vacancyRepository.save(VACANCY_ENTITY.copy(id = 2, name = "Vacante 2", salaryExpectation = 3000))
        val savedVacancy3 = vacancyRepository.save(VACANCY_ENTITY.copy(id = 3, name = "Vacante 3", salaryExpectation = 8000))
        val savedVacancy4 = vacancyRepository.save(VACANCY_ENTITY.copy(id = 4, name = "Vacante 4", salaryExpectation = 9000))
        // when
        val response = mockMvc.get("/api/v1/vacancy/search?salary=5000")
        // then
        response
            .andDo { print() }
            .andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$", hasSize<Int>(2)) // Ensure the response list has a size of 2
        }
    }
}