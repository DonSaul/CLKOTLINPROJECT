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
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*


class VacancyServiceUnitTest {
    @Mock
    private lateinit var vacancyRepository: VacancyRepository
    @Mock
    private lateinit var jobFamilyService: JobFamilyService
    @Mock
    private lateinit var userService: UserService
    @InjectMocks
    private lateinit var vacancyService: VacancyService

    lateinit var vacancyEntity: Vacancy
    lateinit var expectedResponseDTO: VacancyResponseDTO
    lateinit var vacancyRequestDTO: VacancyRequestDTO

    companion object {
        val ID = 1
        val NAME = "Vacante 1"
        val COMPANY_NAME = "Important Company"
        val SALARY_EXPECTATION = 10000
        val YEARS_OF_EXPERIENCE = 3
        val DESCRIPTION = "BLABLABLA"
        val JOB_FAMILY = JobFamily(1, "Developer")
        val MANAGER_1 = User(
            id = 1,
            firstName = "Mana",
            lastName = "Ger",
            email = "manager@mail.com",
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
    }


    @BeforeEach
        fun setUp() {
            vacancyEntity = Vacancy(
                ID,
                NAME,
                COMPANY_NAME,
                SALARY_EXPECTATION,
                YEARS_OF_EXPERIENCE,
                DESCRIPTION,
                JOB_FAMILY,
                MANAGER_1
            )

            expectedResponseDTO = VacancyResponseDTO(
                id = ID,
                name = NAME,
                salaryExpectation = SALARY_EXPECTATION,
                yearsOfExperience = YEARS_OF_EXPERIENCE,
                managerId = MANAGER_1.id,
                jobFamilyId = JOB_FAMILY.id!!,
                jobFamilyName = JOB_FAMILY.name,
                companyName = COMPANY_NAME,
                description = DESCRIPTION
            )
            vacancyRequestDTO = VacancyRequestDTO(
                id = null,
                name = NAME,
                salaryExpectation = SALARY_EXPECTATION,
                yearsOfExperience = YEARS_OF_EXPERIENCE,
                jobFamilyId = JOB_FAMILY.id!!,
                companyName = COMPANY_NAME,
                description = DESCRIPTION
            )
            MockitoAnnotations.openMocks(this)
        }

    @Test
    fun `Should retrieve vacancy by id`() {
        val vacancyId = vacancyEntity.id!!
        val vacancyEntity = vacancyEntity
        val expectedResponseDTO = expectedResponseDTO
        // Mocking behavior of vacancyRepository
        `when`(vacancyRepository.findById(vacancyId)).thenReturn(Optional.of(vacancyEntity))

        // Calling the method under test
        val result = vacancyService.retrieveVacancy(vacancyId)

        // Verifying the result
        Assertions.assertEquals(expectedResponseDTO, result)
    }

    @Test
    fun `Should throw NotFoundException when retrieving vacancy by id`() {
        val vacancyId = vacancyEntity.id!!
        val emptyOptional: Optional<Vacancy> = Optional.empty()
        // Mocking behavior of vacancyRepository
        `when`(vacancyRepository.findById(vacancyId)).thenReturn(emptyOptional)

        // Calling the method under test
        Assertions.assertThrows(NotFoundException::class.java) {
            vacancyService.retrieveVacancy(vacancyId)
        }
        // Verifying the result
        verify(vacancyRepository, times(1)).findById(vacancyId)
    }

    @Test
    fun `Should create vacancy and return response`() {
        // Given
        `when`(jobFamilyService.findByJobFamilyId(vacancyRequestDTO.jobFamilyId)).thenReturn(JOB_FAMILY)
        `when`(userService.retrieveAuthenticatedUser()).thenReturn(MANAGER_1)
        `when`(vacancyRepository.save(any())).thenReturn(vacancyEntity)

        // When
        val result = vacancyService.createVacancy(vacancyRequestDTO)

        // Then
        Assertions.assertEquals(expectedResponseDTO, result)

        // Verify mocks
        verify(jobFamilyService).findByJobFamilyId(vacancyRequestDTO.jobFamilyId)
        verify(userService).retrieveAuthenticatedUser()
        verify(vacancyRepository).save(any())
    }

    @Test
    fun `When user is not vacancy manager should throw ForbiddenException`() {
        // Given
        val differentManager = MANAGER_2
        `when`(userService.retrieveAuthenticatedUser()).thenReturn(differentManager)
        `when`(vacancyRepository.findById(ID)).thenReturn(Optional.of(vacancyEntity))

        // When
        Assertions.assertThrows(ForbiddenException::class.java){
            vacancyService.deleteVacancy(ID)
        }

        // Then
        // Verify that the vacancyRepository delete method was not called
        verify(vacancyRepository, never()).delete(any())
    }

    @Test
    fun `Should delete Vacancy and vacancyRepository delete should be executed one time`(){
        `when`(userService.retrieveAuthenticatedUser()).thenReturn(MANAGER_1)
        `when`(vacancyRepository.findById(ID)).thenReturn(Optional.of(vacancyEntity))
        //when
        vacancyService.deleteVacancy(ID)
        //then
        verify(vacancyRepository).delete(any())
        verify(vacancyRepository, times(1)).delete(any())

    }
}