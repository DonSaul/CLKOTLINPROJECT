package com.jobsearch.controller

import com.jobsearch.dto.ProfileDTO
import com.jobsearch.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/profiles")
class ProfileController(
    private val userService: UserService
) {
    @GetMapping()
    fun getAllProfiles(): ResponseEntity<List<ProfileDTO>> {
        val profiles = userService.getAllProfiles()
        return ResponseEntity.ok(profiles)
    }

     @GetMapping("/my-profile")
     fun getMyProfileInfo(): ResponseEntity<ProfileDTO> {
         val currentUser = userService.retrieveAuthenticatedUser()
         val profileInfo = userService.getUserProfileInfo(currentUser.id!!)
         return ResponseEntity(profileInfo, HttpStatus.OK)
    }

    @GetMapping("/{userId}")
    fun getUserProfileInfo(@PathVariable userId: Int): ResponseEntity<ProfileDTO> {
        val profileInfo = userService.getUserProfileInfo(userId)
        return ResponseEntity(profileInfo, HttpStatus.OK)
    }

    @PutMapping("/{userId}")
    fun updateUserProfile(@PathVariable userId: Int, @RequestBody updatedProfile: ProfileDTO): ResponseEntity<ProfileDTO> {
        val updatedInfo = userService.updateUserProfile(userId, updatedProfile)
        return ResponseEntity(updatedInfo, HttpStatus.OK)
    }
}