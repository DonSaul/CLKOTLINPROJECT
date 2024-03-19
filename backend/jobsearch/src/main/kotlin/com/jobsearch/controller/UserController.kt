package com.jobsearch.controller

import com.jobsearch.dto.UserRequestDTO
import com.jobsearch.dto.UserResponseDTO
import com.jobsearch.dto.NotificationDTO
import com.jobsearch.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/users")
class UserController(private val userService: UserService) {

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('admin')")
    fun addUser(@Valid @RequestBody userDTO: UserRequestDTO): ResponseEntity<UserResponseDTO> {
        val user = userService.createUser(userDTO)
        return ResponseEntity(user, HttpStatus.CREATED)
    }

    @GetMapping("/{userId}")
    fun retrieveUser(@PathVariable userId: Int): ResponseEntity<UserResponseDTO> {
        val user = userService.retrieveUser(userId)
        return ResponseEntity(user, HttpStatus.OK)
    }

    @GetMapping("/all")
    fun retrieveAllUsers(): ResponseEntity<List<UserResponseDTO>> {
        val users = userService.retrieveAllUsers()
        return ResponseEntity(users, HttpStatus.OK)
    }

    @PutMapping("/{userId}")
    fun updateUser(@PathVariable userId: Int,@Valid @RequestBody userRequestDTO: UserRequestDTO): ResponseEntity<UserResponseDTO> {
        val updatedUser = userService.updateUser(userId, userRequestDTO)
        return ResponseEntity(updatedUser, HttpStatus.OK)
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: Int): ResponseEntity<String> {
        val result = userService.deleteUser(userId)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @PutMapping("/notification/{email}/{isActivate}")
    fun updateUserNotification(
        @PathVariable email: String,
        @PathVariable isActivate: Boolean
    ): ResponseEntity<UserResponseDTO> {
        val updatedUser: UserResponseDTO = if (isActivate) {
            userService.activateNotifications(email)
        } else {
            userService.deactivateNotifications(email)
        }
        return ResponseEntity.ok(updatedUser)
    }

    @PutMapping("{email}/activateNotificationType/{notificationTypeId}")
    fun activateNotificationType(
        @PathVariable email: String,
        @PathVariable notificationTypeId: Int
    ): ResponseEntity<UserResponseDTO> {
        val updatedUser = userService.activatedNotificationTypes(email, notificationTypeId)
        return ResponseEntity(updatedUser, HttpStatus.OK)
    }
}