package com.jobsearch.service

import com.jobsearch.dto.VacancyRequestDTO
import com.jobsearch.dto.VacancyResponseDTO
import com.jobsearch.entity.JobFamily
import com.jobsearch.entity.Role
import com.jobsearch.entity.User
import com.jobsearch.entity.Vacancy
import com.jobsearch.exception.ForbiddenException
import com.jobsearch.exception.NotFoundException
import com.jobsearch.repository.VacancyRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.Mockito.never
import org.mockito.MockitoAnnotations
import java.util.Optional

class VacancyServiceUnitTest {
    @Mock
    private lateinit var vacancyRepository: VacancyRepository
    @Mock
    private lateinit var jobFamilyService: JobFamilyService
    @Mock
    private lateinit var userService: UserService
    @InjectMocks
    private lateinit var vacancyService: VacancyService

    companion object {
        private val managerRole = Role(2, "manager")
        val JOB_FAMILY = JobFamily(1, "Information Technology")
        val MANAGER_1 = User(
            id = 1,
            firstName = "Mana",
            lastName = "Ger",
            email = "manager@mail.com",
            password = "test123",
            role = managerRole
        )
        val MANAGER_2 = User(
            id = 2,
            firstName = "Mana2",
            lastName = "Ger2",
            email = "manager2@mail.com",
            password = "test123",
            role = managerRole
        )
        val VACANCY_1 = Vacancy(
            id = 1,
            name = "Vacancy one",
            companyName = "Important Company",
            salaryExpectation = 10000,
            yearsOfExperience = 3,
            description = "Vacancy one description",
            jobFamily = JOB_FAMILY,
            manager = MANAGER_1
        )
        val EXPECTED_RESPONSE_DTO = VACANCY_1.let {
            VacancyResponseDTO(
                id = it.id!!,
                name = it.name,
                salaryExpectation = it.salaryExpectation,
                yearsOfExperience = it.yearsOfExperience,
                managerId = it.manager.id!!,
                jobFamilyId = it.jobFamily.id!!,
                jobFamilyName = it.jobFamily.name,
                companyName = it.companyName,
                description = it.description
            )
        }
        val VACANCY_REQUEST_DTO = VACANCY_1.let {
            VacancyRequestDTO(
                name = it.name,
                salaryExpectation = it.salaryExpectation,
                yearsOfExperience = it.yearsOfExperience,
                jobFamilyId = it.jobFamily.id!!,
                companyName = it.companyName,
                description = it.description
            )
        }
    }

    @BeforeEach
        fun setUp() {
            MockitoAnnotations.openMocks(this)
        }

    @Test
    fun `Should retrieve vacancy by id`() {
        val vacancyId = VACANCY_1.id!!
        val vacancyEntity = VACANCY_1
        val expectedResponseDTO = EXPECTED_RESPONSE_DTO
        // Mocking behavior of vacancyRepository
        `when`(vacancyRepository.findById(vacancyId)).thenReturn(Optional.of(vacancyEntity))
        // Calling the method under test
        val result = vacancyService.retrieveVacancy(vacancyId)
        // Verifying the result
        Assertions.assertEquals(expectedResponseDTO, result)
        verify(vacancyRepository).findById(vacancyId)
    }

    @Test
    fun `Should throw NotFoundException when there is no vacancy on the repository`() {
        val vacancyId = VACANCY_1.id!!
        val emptyOptional: Optional<Vacancy> = Optional.empty()
        `when`(vacancyRepository.findById(vacancyId)).thenReturn(emptyOptional)
        // Calling the method under test
        Assertions.assertThrows(NotFoundException::class.java) {
            vacancyService.retrieveVacancy(vacancyId)
        }
        verify(vacancyRepository).findById(vacancyId)

    }

    @Test
    fun `Should create vacancy and return VacancyResponseDTO`() {
        // Given
        `when`(jobFamilyService.findByJobFamilyId(VACANCY_REQUEST_DTO.jobFamilyId)).thenReturn(JOB_FAMILY)
        `when`(userService.retrieveAuthenticatedUser()).thenReturn(MANAGER_1)
        `when`(vacancyRepository.save(any())).thenReturn(VACANCY_1)

        // When
        val result = vacancyService.createVacancy(VACANCY_REQUEST_DTO)

        // Then
        Assertions.assertEquals(EXPECTED_RESPONSE_DTO, result)

        // Verify mocks
        verify(jobFamilyService).findByJobFamilyId(VACANCY_REQUEST_DTO.jobFamilyId)
        verify(userService).retrieveAuthenticatedUser()
        verify(vacancyRepository).save(any())
    }

    @Test
    fun `When user is not vacancy manager and try to delete Vacancy it should throw ForbiddenException`() {
        // Given
        val differentManager = MANAGER_2
        `when`(userService.retrieveAuthenticatedUser()).thenReturn(differentManager)
        `when`(vacancyRepository.findById(VACANCY_1.id!!)).thenReturn(Optional.of(VACANCY_1))

        // When
        Assertions.assertThrows(ForbiddenException::class.java){
            vacancyService.deleteVacancy(VACANCY_1.id!!)
        }

        // Verify that the vacancyRepository delete method was not called
        verify(vacancyRepository, never()).delete(any())
        verify(userService).retrieveAuthenticatedUser()
    }

    @Test
    fun `Should delete Vacancy and vacancyRepository delete should be executed just once`(){
        `when`(userService.retrieveAuthenticatedUser()).thenReturn(MANAGER_1)
        `when`(vacancyRepository.findById(VACANCY_1.id!!)).thenReturn(Optional.of(VACANCY_1))
        //when
        vacancyService.deleteVacancy(VACANCY_1.id!!)
        //then
        verify(vacancyRepository).delete(any())
        verify(vacancyRepository).delete(any())

    }
}

