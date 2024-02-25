package com.jobsearch.service

import com.jobsearch.dto.UserDTO
import com.jobsearch.entity.User
import com.jobsearch.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun createUser(userDTO: UserDTO): UserDTO {
        val encodedPassword = passwordEncoder.encode(userDTO.password)

        val userEntity = User(
            lastName = userDTO.lastName,
            firstName = userDTO.firstName,
            password = encodedPassword,
            email = userDTO.email,
        )

        val newUser = userRepository.save(userEntity)

        return UserDTO(newUser.id!!, newUser.lastName, newUser.firstName, newUser.email, newUser.password)
    }
    fun retrieveUser(userId: Int): UserDTO {
        val user = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("No user found with id $userId") }

        return user.let {
            UserDTO(it.id!!, it.lastName, it.firstName, it.email, it.password)
        }
    }

    fun retrieveAllUsers(): List<UserDTO> {
        val users = userRepository.findAll()

        return users.map { user ->
            UserDTO(user.id!!, user.lastName, user.firstName, user.email, user.password)
        }
    }

    fun updateUser(userId: Int, userDTO: UserDTO): UserDTO {
        val user = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("No user found with id $userId") }

        user.apply {
            lastName = userDTO.lastName
            firstName = userDTO.firstName
            password = passwordEncoder.encode(userDTO.password)
            email = userDTO.email
        }

        val updatedUser = userRepository.save(user)

        return updatedUser.let {
            UserDTO(it.id!!, it.lastName, it.firstName, it.email, it.password)
        }
    }

    fun deleteUser(userId: Int): String {
        val user = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("No user found with id $userId") }

        userRepository.delete(user)

        return "User deleted successfully"
    }
}