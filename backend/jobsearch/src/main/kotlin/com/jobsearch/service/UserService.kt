package com.jobsearch.service

import com.jobsearch.dto.UserRequestDTO
import com.jobsearch.dto.UserResponseDTO
import com.jobsearch.entity.User
import com.jobsearch.repository.RoleRepository
import com.jobsearch.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun createUser(userRequestDTO: UserRequestDTO): UserResponseDTO? {

        val existingUser = userRepository.findByEmail(userRequestDTO.email)

        if (existingUser.isPresent) {
        //Handle this with a code later
            return null
        }

        val encodedPassword = passwordEncoder.encode(userRequestDTO.password)
        val roleId = userRequestDTO.roleId?:1
        val userEntity = User(
            firstName = userRequestDTO.firstName,
            lastName = userRequestDTO.lastName,
            password = encodedPassword,
            email = userRequestDTO.email,
            role = roleRepository.findById(roleId).get()
        )

        val newUser = userEntity.let { userRepository.save(it) }
        return mapToUserResponseDTO(newUser)
    }

    @Transactional
    fun retrieveUser(userId: Int): UserResponseDTO {
        val user = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("No user found with id $userId") }
        return mapToUserResponseDTO(user)
    }

    @Transactional
    fun retrieveAllUsers(): List<UserResponseDTO> {
        val users = userRepository.findAll()
        return users.map {
            mapToUserResponseDTO(it)
        }
    }

    @Transactional
    fun updateUser(userId: Int, userRequestDTO: UserRequestDTO): UserResponseDTO {
        val user = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("No user found with id $userId") }
        user.apply {
            firstName = userRequestDTO.firstName
            lastName = userRequestDTO.lastName
            if (userRequestDTO.password.isNotEmpty()) {
                password = passwordEncoder.encode(userRequestDTO.password)
            }
            email = userRequestDTO.email
        }

        val updatedUser = userRepository.save(user)
        return mapToUserResponseDTO(updatedUser)
    }

    @Transactional
    fun deleteUser(userId: Int): String {
        val user = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("No user found with id $userId") }
        userRepository.delete(user)
        return "User deleted successfully"
    }

    fun retrieveAuthenticatedUser(): User {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val email: String = authentication.name
        return userRepository.findByEmail(email)
            .orElseThrow { NoSuchElementException("No user found with email $email") }
    }

    fun mapToUserResponseDTO(userEntity: User): UserResponseDTO {
        return userEntity.let{
            UserResponseDTO(
                it.id!!,
                it.firstName,
                it.lastName,
                it.email,
                it.role!!.id!!
            )
        }
    }
}