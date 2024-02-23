package com.jobsearch.controller

import com.jobsearch.dto.UserDTO
import com.jobsearch.entity.User
import com.jobsearch.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping("/create")
    fun addUser(@RequestBody userDTO: UserDTO): ResponseEntity<User> {
        val user = userService.createUser(userDTO)
        return ResponseEntity(user, HttpStatus.CREATED)
    }

    @GetMapping("/{userId}")
    fun retrieveUser(@PathVariable userId: Long): ResponseEntity<UserDTO> {
        val user = userService.retrieveUser(userId)
        return ResponseEntity(user, HttpStatus.OK)
    }

    @GetMapping("/all")
    fun retrieveAllUsers(): ResponseEntity<List<UserDTO>> {
        val users = userService.retrieveAllUsers()
        return ResponseEntity(users, HttpStatus.OK)
    }

    @PutMapping("/{userId}")
    fun updateUser(@PathVariable userId: Long, @RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        val updatedUser = userService.updateUser(userId, userDTO)
        return ResponseEntity(updatedUser, HttpStatus.OK)
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: Long): ResponseEntity<String> {
        val result = userService.deleteUser(userId)
        return ResponseEntity(result, HttpStatus.OK)
    }
}