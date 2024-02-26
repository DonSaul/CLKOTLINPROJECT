package com.jobsearch.service

import com.jobsearch.dto.UserDTO
import com.jobsearch.entity.User
import com.jobsearch.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository,

) {
    @Transactional
    fun createUser(userDTO: UserDTO): UserDTO {


        val userEntity = User(
            firstName = userDTO.firstName,
            lastName = userDTO.lastName,
            password = userDTO.password,
            email = userDTO.email,
        )

        val newUser = userRepository.save(userEntity)

        return UserDTO(newUser.id!!, newUser.firstName,newUser.lastName, newUser.email, newUser.password)
    }
    @Transactional
    fun retrieveUser(userId: Int): UserDTO {
        val user = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("No user found with id $userId") }

        return user.let {
            UserDTO(it.id!!, it.firstName, it.lastName, it.email, it.password)
        }
    }

    fun retrieveAllUsers(): List<UserDTO> {
        val users = userRepository.findAll()

        return users.map { user ->
            UserDTO(user.id!!,  user.firstName,user.lastName, user.email, user.password)
        }
    }

    fun updateUser(userId: Int, userDTO: UserDTO): UserDTO {
        val user = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("No user found with id $userId") }

        user.apply {
            firstName = userDTO.firstName
            lastName = userDTO.lastName
            password = userDTO.password
            email = userDTO.email
        }

        val updatedUser = userRepository.save(user)

        return updatedUser.let {
            UserDTO(it.id!!, it.firstName, it.lastName, it.email, it.password)
        }
    }

    fun deleteUser(userId: Int): String {
        val user = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("No user found with id $userId") }

        userRepository.delete(user)

        return "User deleted successfully"
    }
}