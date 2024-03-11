package com.jobsearch.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jobsearch.entity.JobFamily
import com.jobsearch.entity.Role
import com.jobsearch.entity.User
import com.jobsearch.entity.Vacancy
import com.jobsearch.repository.UserRepository
import com.jobsearch.service.VacancyService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(controllers = [VacancyController::class])
class VacancyControllerTest(
    val mockMvc: MockMvc,
    @MockBean val vacancyService: VacancyService,
    @MockBean val userRepository: UserRepository,
    val objectMapper: ObjectMapper
) {
    @Test
    fun `Test 1 - Create`(){
        // given
        val managerRole = Role(id = 2, name = "manager")

        val manager = User(
            firstName = "Manager",
            lastName = "Apellido",
            email = "manage@manager.com",
            password = "abc123",
            role = managerRole
        )

        val vacancy = Vacancy(
            id = 1,
            name = "Vacante 1",
            salaryExpectation =  10000,
            yearsOfExperience = 3,
            manager = manager,
            jobFamily = JobFamily(id = 1, name = "Dev"),
            companyName = "Important Company",
            description = "BLABLABLA"
        )
        BDDMockito.given(userRepository.save(manager))

        // when

        // then

    }
}