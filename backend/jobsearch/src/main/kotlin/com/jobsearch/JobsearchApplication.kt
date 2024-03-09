package com.jobsearch

import com.jobsearch.dto.UserDTO
import com.jobsearch.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
class JobsearchApplication {
	@Bean
	fun passwordEncoder(): PasswordEncoder {
		return BCryptPasswordEncoder()
	}

	@Bean
	fun init(userService: UserService): CommandLineRunner {
		return CommandLineRunner {

			val userDTO = UserDTO(
					firstName = "Managerio",
					lastName = "Mangolio",
					email = "mana@mana",
					password = "1234",
					roleId = 2
			)

			userService.createUser(userDTO)

			val admin = UserDTO(
					firstName = "Admino",
					lastName = "Admalio",
					email = "admin@admin",
					password = "1234",
					roleId = 3
			)

			userService.createUser(admin)

			val candidate = UserDTO(
					firstName = "Can",
					lastName = "Didate",
					email = "can@can",
					password = "1234",
					roleId = 1
			)

			userService.createUser(candidate)

		}
	}
}

fun main(args: Array<String>) {
	runApplication<JobsearchApplication>(*args)
}