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
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional // Reverts the changes made to the database after finish
class NotificationControllerIntgTest {
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
}