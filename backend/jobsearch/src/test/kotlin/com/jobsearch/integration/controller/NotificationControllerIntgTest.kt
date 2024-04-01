package com.jobsearch.integration.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jobsearch.dto.CvRequestDTO
import com.jobsearch.dto.JobRequestDTO
import com.jobsearch.dto.ProjectRequestDTO
import com.jobsearch.dto.VacancyRequestDTO
import com.jobsearch.entity.*
import com.jobsearch.repository.CvRepository
import com.jobsearch.repository.UserRepository
import com.jobsearch.repository.VacancyRepository
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.put
import java.time.LocalDate

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
    lateinit var cvRepository: CvRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper

    // Will be initialized in setUp
    lateinit var MANAGER_1: User
    lateinit var MANAGER_2: User
    lateinit var CANDIDATE_1: User
    lateinit var VACANCY_ENTITY: Vacancy

    lateinit var CV_ENTITY: Cv
    lateinit var PROJECT: Project
    lateinit var PROJECT_REQUEST: ProjectRequestDTO
    lateinit var JOB_ENTITY: Job
    lateinit var JOB_REQUEST: JobRequestDTO
    lateinit var CV_REQUEST: CvRequestDTO

    lateinit var VACANCY_REQUEST: VacancyRequestDTO

    companion object {

        val JOB_FAMILY = JobFamily(1, "Information Technology")
        val JOB_FAMILY_2 = JobFamily(2, "Sales")
        val MANAGER_1 = User(
            id = null,
            firstName = "Mana",
            lastName = "Ger",
            email = "manager1@mail.com",
            password = "test123",
            role = Role(1, "manager")
        )
        val MANAGER_2 = User(
            id = null,
            firstName = "Mana2",
            lastName = "Ger2",
            email = "manager2@mail.com",
            password = "test123",
            role = Role(1, "manager")
        )
        val CANDIDATE_1 = User(
            id = null,
            firstName = "Cand",
            lastName = "Ide",
            email = "candidate1@mail.com",
            password = "test123",
            role = Role(2, "candidate")
        )

        val CV_ENTITY = Cv(
            id = null,
            summary = "blablabla",
            yearsOfExperience = 1,
            salaryExpectation = 10000,
            education = "High School",
            user = CANDIDATE_1,
            summary = "Summary 1"
        )

        val CV_REQUEST = CV_ENTITY.let {
            CvRequestDTO(
                summary = "blablabla",
                yearsOfExperience = it.yearsOfExperience,
                salaryExpectation = it.salaryExpectation,
                education = it.education,
                jobs = listOf(JOB_REQUEST),
                projects = listOf(PROJECT_REQUEST),
                skillIds = setOf(1),
                summary=it.summary
            )
        }

        val PROJECT_ENTITY = Project(
            id = null,
            name = "Project 1",
            description = "BLABLABLA",
            cv = CV_ENTITY,
            jobFamily = JOB_FAMILY
        )

        val PROJECT_REQUEST = PROJECT_ENTITY.let {
            ProjectRequestDTO(
                id = null,
                name = it.name,
                description = it.description,
                jobFamilyId = it.jobFamily.id!!
            )
        }


        val JOB_ENTITY = Job(
            id = null,
            startDate = LocalDate.of(2022, 2, 2),
            endDate = LocalDate.now(),
            company = "Important Company",
            position = "Position 1",
            description = "BLABLABLA",
            cv = CV_ENTITY,
            jobFamily = JOB_FAMILY_2,
            company = "Company 1"
        )

        val JOB_REQUEST = JOB_ENTITY.let {
            JobRequestDTO(
                id = null,
                startDate = it.startDate,
                endDate = it.endDate,
                company = "Important Company",
                position = it.position,
                description = it.description,
                jobFamilyId = it.jobFamily.id!!,
                company = it.company
            )
        }


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


        val VACANCY_REQUEST = VACANCY_ENTITY.let {
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
        cvRepository.deleteAll()

        MANAGER_1 = userRepository.save(NotificationControllerIntgTest.MANAGER_1)
        MANAGER_2 = userRepository.save(NotificationControllerIntgTest.MANAGER_2)
        CANDIDATE_1 = userRepository.save(NotificationControllerIntgTest.CANDIDATE_1)
        CV_ENTITY = cvRepository.save(NotificationControllerIntgTest.CV_ENTITY)

        VACANCY_ENTITY = NotificationControllerIntgTest.VACANCY_ENTITY
        VACANCY_REQUEST = NotificationControllerIntgTest.VACANCY_REQUEST
        PROJECT = PROJECT_ENTITY
        PROJECT_REQUEST = NotificationControllerIntgTest.PROJECT_REQUEST
        JOB_ENTITY = NotificationControllerIntgTest.JOB_ENTITY
        JOB_REQUEST = NotificationControllerIntgTest.JOB_REQUEST
        CV_ENTITY = NotificationControllerIntgTest.CV_ENTITY
        CV_REQUEST = NotificationControllerIntgTest.CV_REQUEST
    }



    @Test
    @WithMockUser(username = "candidate1@mail.com", authorities = ["candidate"])
    fun `Should create a project and job`() {
        val cvId = CV_ENTITY.id
        val response = mockMvc.put("/api/v1/cvs/{id}", cvId) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(CV_REQUEST)
        }

        response
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }
}