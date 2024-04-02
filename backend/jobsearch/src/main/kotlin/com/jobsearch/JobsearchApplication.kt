package com.jobsearch

import com.jobsearch.dto.*
import com.jobsearch.dto.override.OverrideCvRequestDTO
import com.jobsearch.entity.*
import com.jobsearch.mock.DataLoaderService
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
        overrideService: OverrideService,
        dataLoaderService: DataLoaderService

    ): CommandLineRunner {
        return CommandLineRunner {
            //Classic Users
            val userRequestDTO = UserRequestDTO(
                firstName = "Managerio",
                lastName = "Mangolio",
                email = "mana@mana",
                password = "1234",
                roleId = 2
            )

            val manager = userService.createUser(userRequestDTO)

            val adminRequestDTO = UserRequestDTO(
                firstName = "Admino",
                lastName = "Admalio",
                email = "admin@admin",
                password = "1234",
                roleId = 3
            )

            val admin = userService.createUser(adminRequestDTO)

            val candidateRequestDTO = UserRequestDTO(
                firstName = "Can",
                lastName = "Didate",
                email = "can@can",
                password = "1234",
                roleId = 1
            )

            val candidate = userService.createUser(candidateRequestDTO)

            //Showcase user
            val candidateShowcaseRequestDTO = UserRequestDTO(
                firstName = "Jamiro",
                lastName = "Smith",
                email = "projectlab.user@gmail.com",
                password = "a123",
                roleId = 1
            )
            //showcase email
            val candidateShowcase = userService.createUser(candidateShowcaseRequestDTO)

            dataLoaderService.createHardCvForCandidateShowcase(candidateShowcase)



            //Vacancies for mana@mana
            dataLoaderService.createHardVacanciesForManager(manager)

            // Can can will have a CV
            dataLoaderService.createHardCvForCandidate(candidate)

            //Add some more candidates
            val createdCandidates=dataLoaderService.createHardCandidates()

            //some new managers
            val createdManagers=dataLoaderService.createHardManagers()



            //load some cvs for the candidates

            createdCandidates.forEach {
                dataLoaderService.createRandomCvForCandidateBySeed(it,it.id)
            }





        }
    }
}

fun main(args: Array<String>) {
    runApplication<JobsearchApplication>(*args)
}