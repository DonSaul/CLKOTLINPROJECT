package com.jobsearch.service

import com.jobsearch.dto.UserDTO
import com.jobsearch.entity.User
import com.jobsearch.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.security.crypto.password.PasswordEncoder

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun createUser(userDTO: UserDTO): User {
        val encodedPassword = passwordEncoder.encode(userDTO.password)

        val userEntity = userDTO.let {
            User(
                id = null,
                lastName = it.lastName,
                firstName = it.firstName,
                password = encodedPassword,
                email = it.email,
                role = roleService.findRoleById(it.roleId)
            )
        }
        val newUser = userRepository.save(userEntity)

        return newUser.let {
            UserDTO(it.id!!, it.lastName, it.firstName, it.email, it.role.id!!)
        }
    }
    fun retrieveUser(userId: Long): UserDTO {
        val user = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("No user found with id $userId") }

        return user.let {
            UserDTO(it.id!!, it.lastName, it.firstName, it.email, it.role.id!!)
        }
    }

    fun retrieveAllUsers(): List<UserDTO> {
        val users = userRepository.findAll()

        return users.map { user ->
            UserDTO(user.id!!, user.lastName, user.firstName, user.email, user.role.id!!)
        }
    }

    fun updateUser(userId: Long, userDTO: UserDTO): UserDTO {
        val user = userRepository.findById(userId.toInt())
            .orElseThrow { NoSuchElementException("No user found with id $userId") }

        user.apply {
            lastName = userDTO.lastName
            firstName = userDTO.firstName
            password = passwordEncoder.encode(userDTO.password)
            email = userDTO.email
        }

        val updatedUser = userRepository.save(user)

        return updatedUser.let {
            UserDTO(it.id!!, it.lastName, it.firstName, it.email, it.role.id!!)
        }
    }

    fun deleteUser(userId: Long): String {
        val user = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("No user found with id $userId") }

        userRepository.delete(user)

        return "User deleted successfully"
    }
}