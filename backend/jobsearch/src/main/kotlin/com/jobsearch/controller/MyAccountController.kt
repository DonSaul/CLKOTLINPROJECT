package com.jobsearch.controller

import com.jobsearch.dto.CvResponseDTO
import com.jobsearch.response.StandardResponse
import com.jobsearch.service.CvService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/my-account")
@Validated
class MyAccountController(
    val cvService: CvService
) {

    @GetMapping("cvs/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun retrieveMyAccountsCv(@PathVariable("id") cvId: Int): ResponseEntity<StandardResponse<CvResponseDTO>> {
        val responseEntity = cvService.retrieveMyAccountsCv(cvId)
        val status = HttpStatus.OK
        val body = StandardResponse(
            status = status.value(),
            data = responseEntity
        )
        return ResponseEntity
            .status(status)
            .body(body)
    }

    @GetMapping("cvs")
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllMyAccountsCvs(): ResponseEntity<StandardResponse<List<CvResponseDTO>>> {
        val responseEntityList = cvService.retrieveAllMyAccountsCvs()
        val status = HttpStatus.OK
        val body = StandardResponse(
            status = status.value(),
            data = responseEntityList
        )
        return ResponseEntity
            .status(status)
            .body(body)
    }

    @GetMapping("cv")
    @ResponseStatus(HttpStatus.OK)
    fun retrieveMyAccountsLastCv(): ResponseEntity<StandardResponse<CvResponseDTO>> {
        val responseEntity = cvService.retrieveMyAccountsLastCv()
        val status = HttpStatus.OK
        val body = StandardResponse(
            status = status.value(),
            data = responseEntity
        )
        return ResponseEntity
            .status(status)
            .body(body)
    }

}