package com.jobsearch.controller

import com.jobsearch.dto.UserDTO
import com.jobsearch.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/users")
class UserController(private val userService: UserService) {

    @PostMapping("/create")
    @PreAuthorize("hasRole('admin')")
    fun addUser(@RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        val user = userService.createUser(userDTO)
        return ResponseEntity(user, HttpStatus.CREATED)
    }

    @GetMapping("/{userId}")
    fun retrieveUser(@PathVariable userId: Int): ResponseEntity<UserDTO> {
        val user = userService.retrieveUser(userId)
        return ResponseEntity(user, HttpStatus.OK)
    }

    @GetMapping("/all")
    fun retrieveAllUsers(): ResponseEntity<List<UserDTO>> {
        val users = userService.retrieveAllUsers()
        return ResponseEntity(users, HttpStatus.OK)
    }

    @PutMapping("/{userId}")
    fun updateUser(@PathVariable userId: Int, @RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        val updatedUser = userService.updateUser(userId, userDTO)
        return ResponseEntity(updatedUser, HttpStatus.OK)
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: Int): ResponseEntity<String> {
        val result = userService.deleteUser(userId)
        return ResponseEntity(result, HttpStatus.OK)
    }
}