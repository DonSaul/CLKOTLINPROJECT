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
        return mapResponseEntity(responseEntity, HttpStatus.CREATED)
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun retrieveCv(@PathVariable("id") cvId: Int): ResponseEntity<StandardResponse<CvResponseDTO>> {
        val responseEntity = cvService.retrieveCv(cvId)
        return mapResponseEntity(responseEntity)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllCvs(): ResponseEntity<StandardResponse<List<CvResponseDTO>>> {
        val responseEntityList = cvService.retrieveAllCvs()
        return mapResponseEntity(responseEntityList)
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateCv(@PathVariable("id") cvId: Int, @Valid @RequestBody cvRequestDTO: CvRequestDTO): ResponseEntity<StandardResponse<CvResponseDTO>> {
        val responseEntity = cvService.updateCv(cvId, cvRequestDTO)
        return mapResponseEntity(responseEntity)
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCv(@PathVariable("id") vacancyId: Int): String {
        return cvService.deleteCv(vacancyId)
    }

    private fun <T> mapResponseEntity(
        dataBody: T,
        status: HttpStatus = HttpStatus.OK
    ): ResponseEntity<StandardResponse<T>> {
        val bodyContent = StandardResponse(
            status = status.value(),
            data = dataBody,
            message = status.reasonPhrase
        )
        return ResponseEntity
            .status(status)
            .body(bodyContent)
    }

    private fun <T> mapResponseEntity(
        dataBody: List<T>,
        status: HttpStatus = HttpStatus.OK
    ): ResponseEntity<StandardResponse<List<T>>> {
        val responseStatus = if (dataBody.isEmpty()) {
            HttpStatus.NOT_FOUND
        } else {
            status
        }
        val bodyContent = StandardResponse(
            status = responseStatus.value(),
            data = dataBody,
            message = responseStatus.reasonPhrase
        )
        return ResponseEntity
            .status(responseStatus)
            .body(bodyContent)
    }

}