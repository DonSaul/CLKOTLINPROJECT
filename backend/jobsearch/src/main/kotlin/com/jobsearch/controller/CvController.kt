package com.jobsearch.controller

import com.jobsearch.dto.CvRequestDTO
import com.jobsearch.dto.CvResponseDTO
import com.jobsearch.response.StandardResponse
import com.jobsearch.service.CvService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/cvs")
@Validated
class CvController(
    val cvService: CvService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCv(@RequestBody @Valid cvRequestDTO: CvRequestDTO): ResponseEntity<StandardResponse<CvResponseDTO>> {
        val responseEntity = cvService.createCv(cvRequestDTO)
        val status = HttpStatus.CREATED
        val body = StandardResponse(
            status = status.value(),
            data = responseEntity
        )
        return ResponseEntity
            .status(status)
            .body(body)
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun retrieveCv(@PathVariable("id") cvId: Int): ResponseEntity<StandardResponse<CvResponseDTO>> {
        val responseEntity = cvService.retrieveCv(cvId)
        val status = HttpStatus.OK
        val body = StandardResponse(
            status = status.value(),
            data = responseEntity
        )
        return ResponseEntity
            .status(status)
            .body(body)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllCvs(): ResponseEntity<StandardResponse<List<CvResponseDTO>>> {
        val responseEntityList = cvService.retrieveAllCvs()
        val status = HttpStatus.OK
        val body = StandardResponse(
            status = status.value(),
            data = responseEntityList
        )
        return ResponseEntity
            .status(status)
            .body(body)
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateCv(@PathVariable("id") cvId: Int, @Valid @RequestBody cvRequestDTO: CvRequestDTO): ResponseEntity<StandardResponse<CvResponseDTO>> {
        val responseEntity = cvService.updateCv(cvId, cvRequestDTO)
        val status = HttpStatus.OK
        val body = StandardResponse(
            status = status.value(),
            data = responseEntity
        )
        return ResponseEntity
            .status(status)
            .body(body)
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCv(@PathVariable("id") vacancyId: Int): String {
        return cvService.deleteCv(vacancyId)
    }

}