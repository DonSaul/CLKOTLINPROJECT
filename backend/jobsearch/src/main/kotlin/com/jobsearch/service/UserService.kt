package com.jobsearch.service

import com.jobsearch.dto.UserRequestDTO
import com.jobsearch.dto.UserResponseDTO
import com.jobsearch.entity.Cv
import com.jobsearch.entity.User
import com.jobsearch.repository.NotificationTypeRepository
import com.jobsearch.exception.NotFoundException
import com.jobsearch.repository.CvRepository
import com.jobsearch.repository.RoleRepository
import com.jobsearch.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.NoSuchElementException


@Service
class UserService @Autowired constructor(
        private val userRepository: UserRepository,
        private val roleRepository: RoleRepository,
        private val passwordEncoder: PasswordEncoder,
        private val notificationTypeRepository: NotificationTypeRepository,
) {
    @Transactional
    fun createUser(userRequestDTO: UserRequestDTO): UserResponseDTO? {

        val existingUser = userRepository.findByEmail(userRequestDTO.email)

        if (existingUser.isPresent) {
            return null
        }

        val encodedPassword = passwordEncoder.encode(userRequestDTO.password)
        val roleId = userRequestDTO.roleId ?: 1
        val userEntity = User(
                firstName = userRequestDTO.firstName,
                lastName = userRequestDTO.lastName,
                password = encodedPassword,
                email = userRequestDTO.email,
                role = roleRepository.findById(roleId).get(),
                resetPasswordToken = null
        )

        val newUser = userEntity.let { userRepository.save(it) }
        return mapToUserResponseDTO(newUser)
    }

    @Transactional
    fun retrieveUser(userId: Int): UserResponseDTO {
        val user = userRepository.findById(userId)
                .orElseThrow { NotFoundException("No user found with id $userId") }
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
                .orElseThrow { NotFoundException("No user found with id $userId") }
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
                .orElseThrow { NotFoundException("No user found with id $userId") }
        userRepository.delete(user)
        return "User deleted successfully"
    }

    fun retrieveAuthenticatedUser(): User {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val email: String = authentication.name
        return userRepository.findByEmail(email)
                .orElseThrow { NotFoundException("No user found with email $email") }
    }

    fun mapToUserResponseDTO(userEntity: User): UserResponseDTO {
        return userEntity.let {
            UserResponseDTO(
                    it.id!!,
                    it.firstName,
                    it.lastName,
                    it.email,
                    it.role!!.id!!,
                    it.notificationActivated,
                    it.activatedNotificationTypes,
                    it.resetPasswordToken
            )
        }
    }

    //!needs test!
    fun activateNotifications(userId: Int): UserResponseDTO {
        val user = userRepository.findById(userId)
                .orElseThrow { NoSuchElementException("No user found with id $userId") }
        user.notificationActivated = true

        val updatedUser = userRepository.save(user)
        return mapToUserResponseDTO(updatedUser)
    }
    fun deactivateNotifications(userId: Int): UserResponseDTO {
        val user = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("No user found with id $userId") }
        user.notificationActivated = false

        val updatedUser = userRepository.save(user)
        return mapToUserResponseDTO(updatedUser)
    }
    fun activatedNotificationTypes(userId: Int, notificationTypeId: Int): UserResponseDTO {
        val user = userRepository.findById(userId)
                .orElseThrow { NoSuchElementException("No user found with id $userId") }

        val notificationType = notificationTypeRepository.findByIdOrNull(notificationTypeId)
            ?: throw NoSuchElementException("No notification type found with id $notificationTypeId")

        user.activatedNotificationTypes = user.activatedNotificationTypes.plus(notificationType)

        val updatedUser = userRepository.save(user)
        return mapToUserResponseDTO(updatedUser)
    }

    fun updateResetPasswordToken(token: String, email: String) {
        val user = userRepository.findByEmail(email)
                .orElseThrow { NoSuchElementException("Could not find any user with the email $email") }
        user.resetPasswordToken = token.toString()
        userRepository.save(user)
    }

    fun updatePassword(user: User, newPassword: String) {
        val passwordEncoder = BCryptPasswordEncoder()
        val encodedPassword = passwordEncoder.encode(newPassword)
        user.password = encodedPassword
        user.resetPasswordToken = null
        userRepository.save(user)
    }


    fun findCandidatesByFilter(salary: Int?, jobFamilyId: Int?, yearsOfExperience: Int?): List<CandidateDTO> {
        val cvs = cvRepository.findCvByFilter(salary, yearsOfExperience)
        val listsOfDto = cvs.map { mapToUserCandidateDTO (it) }
        return listsOfDto
    }
    fun mapToUserCandidateDTO(cvEntity: Cv): CandidateDTO {
        return cvEntity.let {
            CandidateDTO(
                it.user.id!!,
                it.user.firstName,
                it.user.lastName,
                it.user.email,
                it.yearsOfExperience,
                it.salaryExpectation
            )
        }
    }
}