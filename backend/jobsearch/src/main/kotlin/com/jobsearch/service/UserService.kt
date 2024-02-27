package com.jobsearch.service

import com.jobsearch.dto.UserDTO
import com.jobsearch.entity.User
import com.jobsearch.repository.RoleRepository
import com.jobsearch.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
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


        return UserDTO(newUser.id, newUser.firstName, newUser.lastName, newUser.email, newUser.password,
<<<<<<< HEAD
            newUser.role?.id!!
=======
            newUser.role!!.id!!
>>>>>>> 3638edbfdc0935abb4a5307aede1193df40b824f
        )


    }
    @Transactional
    fun retrieveUser(userId: Int): UserDTO {
        val user = userRepository.findById(userId.toLong())
            .orElseThrow { NoSuchElementException("No user found with id $userId") }

        return user.let {
<<<<<<< HEAD
            UserDTO(it.id!!, it.firstName, it.lastName, it.email, it.password, it.role?.id!!)
=======
            UserDTO(it.id!!, it.firstName, it.lastName, it.email, it.password, it.role!!.id)
>>>>>>> 3638edbfdc0935abb4a5307aede1193df40b824f
        }
    }
    @Transactional
    fun retrieveAllUsers(): List<UserDTO> {
        val users = userRepository.findAll()


        return users.map { user ->
<<<<<<< HEAD
            UserDTO(user.id!!,  user.firstName,user.lastName, user.email, user.password, user.role?.id!!)
=======
            UserDTO(user.id!!,  user.firstName,user.lastName, user.email, user.password, user.role!!.id)
>>>>>>> 3638edbfdc0935abb4a5307aede1193df40b824f
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
<<<<<<< HEAD
            UserDTO(it.id!!, it.firstName, it.lastName, it.email, it.password, it.role?.id!!)
=======
            UserDTO(it.id!!, it.firstName, it.lastName, it.email, it.password, it.role!!.id)
>>>>>>> 3638edbfdc0935abb4a5307aede1193df40b824f
        }
    }
    @Transactional
    fun deleteUser(userId: Int): String {
        val user = userRepository.findById(userId.toLong())
            .orElseThrow { NoSuchElementException("No user found with id $userId") }

        userRepository.delete(user)

        return "User deleted successfully"
    }
}