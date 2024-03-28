package com.jobsearch

import com.jobsearch.dto.*
import com.jobsearch.dto.override.OverrideCvRequestDTO
import com.jobsearch.entity.*
import com.jobsearch.repository.CvRepository
import com.jobsearch.repository.UserRepository
import com.jobsearch.service.*
import com.jobsearch.service.override.OverrideService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate

@SpringBootApplication
@Configuration
@EnableAsync
class JobsearchApplication {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun init(
        userService: UserService,
        vacancyService: VacancyService,
        jobFamilyService: JobFamilyService,
        userRepository: UserRepository,
        cvRepository: CvRepository,
        cvService: CvService,
        overrideService: OverrideService
    ): CommandLineRunner {
        return CommandLineRunner {
            //Users
            val userRequestDTO = UserRequestDTO(
                firstName = "Managerio",
                lastName = "Mangolio",
                email = "mana@mana",
                password = "1234",
                roleId = 2
            )

            val manager = userService.createUser(userRequestDTO)

            val admin = UserRequestDTO(
                firstName = "Admino",
                lastName = "Admalio",
                email = "admin@admin",
                password = "1234",
                roleId = 3
            )

            userService.createUser(admin)

            val candidate = UserRequestDTO(
                firstName = "Can",
                lastName = "Didate",
                email = "can@can",
                password = "1234",
                roleId = 1
            )

            userService.createUser(candidate)


            //Vacancies for mana@mana

            val vacancyDTOs = listOf(
                VacancyRequestDTO(
                    id = 1,
                    name = "Software Engineer",
                    companyName = "Google",
                    salaryExpectation = 50000,
                    yearsOfExperience = 2,
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    jobFamilyId = 1
                ),
                VacancyRequestDTO(
                    id = 2,
                    name = "Software Engineer Junior",
                    companyName = "Google",
                    salaryExpectation = 60000,
                    yearsOfExperience = 1,
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    jobFamilyId = 1
                ),
                VacancyRequestDTO(
                    id = 3,
                    name = "Software Engineer Senior",
                    companyName = "Microsoft",
                    salaryExpectation = 99999,
                    yearsOfExperience = 1,
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    jobFamilyId = 1
                ),
                VacancyRequestDTO(
                    id = 4,
                    name = "DevOps Engineer",
                    companyName = "Microsoft",
                    salaryExpectation = 66666,
                    yearsOfExperience = 4,
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    jobFamilyId = 1
                ),
                VacancyRequestDTO(
                    id = 5,
                    name = "Tax Consultant",
                    companyName = "Amazon",
                    salaryExpectation = 123123,
                    yearsOfExperience = 10,
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    jobFamilyId = 2
                ),
                VacancyRequestDTO(
                    id = 5,
                    name = "Financial Planner",
                    companyName = "Netflix",
                    salaryExpectation = 121212,
                    yearsOfExperience = 10,
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    jobFamilyId = 2
                ),
                VacancyRequestDTO(
                    id = 6,
                    name = "Physician",
                    companyName = "HealthInc",
                    salaryExpectation = 232323,
                    yearsOfExperience = 10,
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    jobFamilyId = 4
                ),
                VacancyRequestDTO(
                    id = 7,
                    name = "Paralegal",
                    companyName = "LawIntegrity",
                    salaryExpectation = 232323,
                    yearsOfExperience = 10,
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    jobFamilyId = 10
                ),
                VacancyRequestDTO(
                    id = 7,
                    name = "Legal consultant",
                    companyName = "LawIntegrity",
                    salaryExpectation = 1111,
                    yearsOfExperience = 1,
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    jobFamilyId = 10
                ),
                VacancyRequestDTO(
                    id = 8,
                    name = "AI Engineer",
                    companyName = "Tesla",
                    salaryExpectation = 1111,
                    yearsOfExperience = 1,
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    jobFamilyId = 1
                ),
                VacancyRequestDTO(
                    id = 9,
                    name = "Mechanical Engineer Master",
                    companyName = "Tesla",
                    salaryExpectation = 456456,
                    yearsOfExperience = 20,
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    jobFamilyId = 6
                ),
                VacancyRequestDTO(
                    id = 10,
                    name = "CHIP Architect Engineer",
                    companyName = "NVIDIA",
                    salaryExpectation = 898989,
                    yearsOfExperience = 25,
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    jobFamilyId = 6
                ),

                )

            for (vacancyDto in vacancyDTOs) {
                val selectedJobFamily = jobFamilyService.findByJobFamilyId(vacancyDto.jobFamilyId)

                val managerUser: User? = manager?.let { mgr ->
                    userRepository.findById(mgr.id).orElse(null)
                }

                val vacancyExists = vacancyService.vacancyRepository.existsById(vacancyDto.id!!)
                if (!vacancyExists) {
                    val vacancyEntity = vacancyDto.let {
                        Vacancy(
                            it.id,
                            it.name,
                            it.companyName,
                            it.salaryExpectation,
                            it.yearsOfExperience,
                            it.description,
                            selectedJobFamily,
                            managerUser!!
                        )
                    }
                    vacancyService.vacancyRepository.save(vacancyEntity)
                }
            }
            //Some cv here

            // CAn can

            val candidateUser: User? = candidate.let { can ->
                userRepository.findByEmail(can.email).orElse(null)
            }

            val userHasCV = cvRepository.findFirstByUserOrderByIdDesc(candidateUser!!)

            if (userHasCV==null){

                val jobRequestDTOList = listOf(
                    JobRequestDTO(
                        id = 1,
                        startDate = LocalDate.of(2019, 1, 1),
                        endDate = LocalDate.of(2022, 1, 1),
                        position = "Software Engineer",
                        description = "Developed web applications using React",
                        jobFamilyId = 1
                    ),
                    JobRequestDTO(
                        id = 2,
                        startDate = LocalDate.of(2022, 2, 1),
                        endDate = LocalDate.of(2024, 3, 1),
                        position = "Senior Sales Expert",
                        description = "Sold lots of stuff",
                        jobFamilyId = 2
                    )

                )
                val projectRequestDTOList = listOf(
                    ProjectRequestDTO(
                        id = 1,
                        name = "Job Search Platform",
                        description = "Developed a job search platform",
                        jobFamilyId = 3
                    ),
                    ProjectRequestDTO(
                        id = 2,
                        name = "Arduno plants",
                        description = "Plants controlled by arduino",
                        jobFamilyId = 4
                    )

                )
                val skillIdSet = setOf(1, 2, 3)

                val cvRequest = OverrideCvRequestDTO(
                    yearsOfExperience = 5,
                    salaryExpectation = 70000,
                    education = "Licenciatura en Ingeniería Informática",
                    jobs =jobRequestDTOList,
                    projects = projectRequestDTOList,
                    skillIds = skillIdSet,
                    user=candidateUser

                )
                overrideService.createCvOverride(cvRequest)






            }



        }
    }
}

fun main(args: Array<String>) {
    runApplication<JobsearchApplication>(*args)
}