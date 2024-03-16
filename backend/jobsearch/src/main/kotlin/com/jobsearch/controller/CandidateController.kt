package com.jobsearch.controller

import com.jobsearch.dto.CandidateDTO
import com.jobsearch.response.StandardResponse
import com.jobsearch.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/candidates")
class CandidateController(private val userService: UserService) {

    @GetMapping("/search")
    fun searchCandidates(
        @RequestParam(required = false) salary: Int?,
        @RequestParam(required = false) jobFamilyId: Int?,
        @RequestParam(required = false) yearsOfExperience: Int?
    ): ResponseEntity<StandardResponse<List<CandidateDTO>>>  {
        val responseEntityList = userService.findCandidatesByFilter(salary, jobFamilyId, yearsOfExperience)
        var status = HttpStatus.OK
        if (responseEntityList.isEmpty()) {
            status = HttpStatus.NO_CONTENT
        }
        val body = StandardResponse(
            status = status.value(),
            data = responseEntityList
        )
        return ResponseEntity
            .status(status)
            .body(body)
    }
}