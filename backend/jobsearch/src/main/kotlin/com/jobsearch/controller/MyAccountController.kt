package com.jobsearch.controller

import com.jobsearch.dto.CvRequestDTO
import com.jobsearch.dto.CvResponseDTO
import com.jobsearch.dto.VacancyDto
import com.jobsearch.service.CvService
import com.jobsearch.service.VacancyService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/my-account")
@Validated
class MyAccountController(
    val cvService: CvService
) {

    @GetMapping("cvs/{id}")
    fun retrieveMyAccountsCv(@PathVariable("id") cvId: Int): CvResponseDTO {
        return cvService.retrieveMyAccountsCv(cvId)
    }

    @GetMapping("cvs")
    fun retrieveAllMyAccountsCvs(): List<CvResponseDTO> {
        return cvService.retrieveAllMyAccountsCvs()
    }

}