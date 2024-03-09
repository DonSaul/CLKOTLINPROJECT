package com.jobsearch

import com.jobsearch.dto.UserDTO
import com.jobsearch.service.UserService
import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@SpringBootApplication
class JobsearchApplication {
	@Bean
	fun passwordEncoder(): PasswordEncoder {
		return BCryptPasswordEncoder()
	}
}

fun main(args: Array<String>) {
	runApplication<JobsearchApplication>(*args)
}

@Component
class DefaultUserCreator(
	val userService: UserService
): InitializingBean {
	override fun afterPropertiesSet() {
		val admin = UserDTO(
			firstName = "Lenny",
			lastName = "Lennard",
			email = "lenny@l.com",
			password = "lenny1",
			roleId = 3
		)
		val manager = UserDTO(
			firstName = "Carl",
			lastName = "Carlson",
			email = "carl@c.com",
			password = "carl1",
			roleId = 2
		)
		val candidate = UserDTO(
			firstName = "Juan",
			lastName = "Topo",
			email = "juan@t.com",
			password = "juan1",
			roleId = 1
		)
		for (user in listOf(admin, manager, candidate)) {
			try {
				userService.createUser(user)
			}
			catch (e: Exception) {
				println("Default User ${user.email} already in db")
			}
		}
	}
}