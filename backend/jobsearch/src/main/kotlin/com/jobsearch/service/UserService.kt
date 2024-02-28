package com.jobsearch.service

import com.jobsearch.dto.UserDTO
import com.jobsearch.entity.User
import com.jobsearch.repository.RoleRepository
import com.jobsearch.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.core.Authentication;


@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun createUser(userDTO: UserDTO): UserDTO {
        val encodedPassword = passwordEncoder.encode(userDTO.password)
        val roleId = userDTO.roleId?:1
        val userEntity = User(
            firstName = userDTO.firstName,
            lastName = userDTO.lastName,
            password = encodedPassword,
            email = userDTO.email,
            role = roleRepository.findById(roleId).get()
        )

        val newUser = userEntity.let { userRepository.save(it) }


        return UserDTO(
            newUser.id,
            newUser.firstName,
            newUser.lastName,
            newUser.email,
            newUser.password,
            newUser.role?.id!!
        )


    }
    @Transactional
    fun retrieveUser(userId: Int): UserDTO {
        val user = userRepository.findById(userId.toLong())
            .orElseThrow { NoSuchElementException("No user found with id $userId") }

        return user.let {
            UserDTO(
                it.id!!,
                it.firstName,
                it.lastName,
                it.email,
                it.password,
                it.role?.id!!)
        }
    }
    @Transactional
    fun retrieveAllUsers(): List<UserDTO> {
        val users = userRepository.findAll()


        return users.map { user ->
            UserDTO(
                user.id!!,
                user.firstName,
                user.lastName,
                user.email,
                user.password,
                user.role?.id!!)
        }
    }
    @Transactional
    fun updateUser(userId: Int, userDTO: UserDTO): UserDTO {
        val user = userRepository.findById(userId.toLong())
            .orElseThrow { NoSuchElementException("No user found with id $userId") }

        user.apply {
            firstName = userDTO.firstName
            lastName = userDTO.lastName
            if (userDTO.password.isNotEmpty()) {
                password = passwordEncoder.encode(userDTO.password)
            }
            email = userDTO.email
        }

        val updatedUser = userRepository.save(user)

        return updatedUser.let {
            UserDTO(
                it.id!!,
                it.firstName,
                it.lastName,
                it.email,
                it.password,
                it.role?.id!!)
        }
    }

    @Transactional
    fun deleteUser(userId: Int): String {
        val user = userRepository.findById(userId.toLong())
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
}