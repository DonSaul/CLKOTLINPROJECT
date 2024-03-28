package com.jobsearch.service

import com.jobsearch.dto.*
import com.jobsearch.entity.Application
import com.jobsearch.entity.Cv
import com.jobsearch.entity.JobFamily
import com.jobsearch.entity.User
import com.jobsearch.exception.ForbiddenException
import com.jobsearch.exception.NotFoundException
import com.jobsearch.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.NoSuchElementException
import com.jobsearch.entity.NotificationTypeEnum



@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val notificationTypeRepository: NotificationTypeRepository,
    private val interestService: InterestService,
    val cvRepository: CvRepository,
    private val notificationTypeService: NotificationTypeService,
    private val vacancyRepository: VacancyRepository,
    private val applicationRepository: ApplicationRepository
) {
    @Transactional
    fun createUser(userRequestDTO: UserRequestDTO): UserResponseDTO? {

        val existingUser = userRepository.findByEmail(userRequestDTO.email)

        if (existingUser.isPresent) {
            return null
        }

        val activatedNotificationTypeEnums = setOf(NotificationTypeEnum.VACANCIES, NotificationTypeEnum.INVITATIONS, NotificationTypeEnum.MESSAGES)
        val notificationTypes = activatedNotificationTypeEnums.map{notificationTypeService.findByIdAndReturnsEntity(it.id)}.toSet()

        val encodedPassword = passwordEncoder.encode(userRequestDTO.password)
        val roleId = userRequestDTO.roleId ?: 1
        val userEntity = User(
            firstName = userRequestDTO.firstName,
            lastName = userRequestDTO.lastName,
            password = encodedPassword,
            email = userRequestDTO.email,
            role = roleRepository.findById(roleId).get(),
            activatedNotificationTypes = notificationTypes ,
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

//    @Transactional
//    fun getAllProfiles(): List<ProfileDTO> {
//        val users = userRepository.findAll()
//        val profiles = mutableListOf<ProfileDTO>()
//
//        for (user in users) {
//            val profileDTO = mapToProfileDTO(user)
//            profiles.add(profileDTO)
//        }
//        return profiles
//    }
//
//    private fun mapToProfileDTO(user: User): ProfileDTO {
//        return ProfileDTO(user.firstName, user.lastName, user.email, user.role, user.cv)
//    }

    fun getUserProfileInfo(userId: Int): ProfileDTO {
        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundException("No user found with id $userId") }
        val cv =  cvRepository.findFirstByUserOrderByIdDesc(user)

        return ProfileDTO(
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            roleId = user.role?.id ?: -1,
            cv = cv.let {
                if (it != null) {
                    mapToCvDTO(it)
                } else {
                    null
                }
            }
        )
    }

    @Transactional
    fun updateUserProfile(userId: Int, updatedProfile: ProfileDTO): ProfileDTO {
        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundException("No user found with id $userId") }

        // Update profile
        user.apply {
            firstName = updatedProfile.firstName
            lastName = updatedProfile.lastName
            email = updatedProfile.email
        }

        val updatedUserProfile = userRepository.save(user)
        return ProfileDTO(
            firstName = updatedUserProfile.firstName,
            lastName = updatedUserProfile.lastName,
            email = updatedProfile.email,
            roleId = updatedUserProfile.role?.id ?: -1
        )
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

    fun activateNotifications(email: String): UserResponseDTO {
        val user = userRepository.findByEmail(email)
            .orElseThrow { NoSuchElementException("Could not find any user with the email $email") }
        user.notificationActivated = true

        val updatedUser = userRepository.save(user)
        return mapToUserResponseDTO(updatedUser)
    }

    fun deactivateNotifications(email: String): UserResponseDTO {
        val user = userRepository.findByEmail(email)
            .orElseThrow { NoSuchElementException("Could not find any user with the email $email") }
        user.notificationActivated = false

        val updatedUser = userRepository.save(user)
        return mapToUserResponseDTO(updatedUser)
    }

    fun activatedNotificationTypes(email: String, notificationTypeId: Int): UserResponseDTO {
        val user = userRepository.findByEmail(email)
            .orElseThrow { NoSuchElementException("Could not find any user with the email $email") }

        val notificationType = notificationTypeRepository.findByIdOrNull(notificationTypeId)
            ?: throw NoSuchElementException("No notification type found with id $notificationTypeId")

        user.activatedNotificationTypes = user.activatedNotificationTypes.plus(notificationType)

        val updatedUser = userRepository.save(user)
        return mapToUserResponseDTO(updatedUser)
    }

    fun deactivateNotificationTypes(email: String, notificationTypeId: Int): UserResponseDTO {
        val user = userRepository.findByEmail(email)
            .orElseThrow { NoSuchElementException("Could not find any user with the email $email") }

        val notificationType = notificationTypeRepository.findByIdOrNull(notificationTypeId)
            ?: throw NoSuchElementException("No notification type found with id $notificationTypeId")

        user.activatedNotificationTypes = user.activatedNotificationTypes.minus(notificationType)

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

        return cvs.mapNotNull { cv ->
            val jobFamilies = cv.user.id?.let { interestService.getJobFamilyByUserId(it) }
            if (jobFamilyId == null || jobFamilies?.any { it.id == jobFamilyId } == true) {
                mapToUserCandidateDTO(cv, jobFamilies)
            } else {
                null
            }
        }
    }

    fun getUserNotificationStatus(email: String): Boolean {
        val user = userRepository.findByEmail(email)
            .orElseThrow { NoSuchElementException("Could not find any user with the email $email") }
        return user.notificationActivated
    }
    fun getActivatedNotificationTypes(email: String): List<NotificationTypeDTO> {
        val user = userRepository.findByEmail(email)
            .orElseThrow { NoSuchElementException("Could not find any user with the email $email") }

        val activatedNotificationTypes = user.activatedNotificationTypes

        val activatedNotificationTypeDTOs = activatedNotificationTypes.map { notificationType ->
            NotificationTypeDTO(
                id = notificationType?.id!!,
                type = notificationType.type,

            )
        }
        return activatedNotificationTypeDTOs
    }


    fun mapToUserCandidateDTO(cvEntity: Cv, jobFamilies: List<JobFamily>?): CandidateDTO {
        return CandidateDTO(
            cvEntity.user.id!!,
            cvEntity.user.firstName,
            cvEntity.user.lastName,
            cvEntity.user.email,
            cvEntity.yearsOfExperience,
            cvEntity.salaryExpectation,
            jobFamilies ?: emptyList()
        )
    }
    fun findCandidatesByVacancyApplication(vacancyId: Int): List<CandidateDTO> {
        val vacancy = vacancyRepository.findById(vacancyId)
            .orElseThrow { NotFoundException("No vacancy found with id $vacancyId") }
        val user = retrieveAuthenticatedUser()
        if (vacancy.manager != user) throw ForbiddenException("You are not authorized to perform this action")
        val applications = applicationRepository.findByVacancy(vacancy)
        return applications.map { mapToUserCandidateDTO(it) }
    }

    fun mapToUserCandidateDTO(application: Application): CandidateDTO {
        return application.let {
            CandidateDTO(
                it.candidate.id!!,
                it.candidate.firstName,
                it.candidate.lastName,
                it.candidate.email,
                it.cv.yearsOfExperience,
                it.cv.salaryExpectation,
                it.cv.user.id?.let { cvUserId -> interestService.getJobFamilyByUserId(cvUserId) },
                it.applicationStatus.name
            )
        }
    }


    fun mapToCvDTO(cv: Cv): CvResponseDTO {
        return CvResponseDTO(
            id = cv.id!!,
            yearsOfExperience = cv.yearsOfExperience,
            salaryExpectation = cv.salaryExpectation,
            education = cv.education,
            projects = cv.projects?.map { project ->
                ProjectResponseDTO(
                    projectId = project.projectId!!,
                    name = project.name,
                    description = project.description,
                    jobFamily = JobFamilyDto(
                        id = project.jobFamily.id!!,
                        name = project.jobFamily.name
                    )
                )
            }?.toSet() ?: emptySet(),
            skills = cv.skills?.map { skill ->
                SkillDTO(
                    skillId = skill.skillId!!,
                    name = skill.name
                )
            }?.toSet() ?: emptySet()
        )
    }
}

