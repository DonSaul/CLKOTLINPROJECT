package com.jobsearch.controller

import com.jobsearch.dto.CvResponseDTO
import com.jobsearch.service.CvService
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
    @ResponseStatus(HttpStatus.OK)
    fun retrieveMyAccountsCv(@PathVariable("id") cvId: Int): CvResponseDTO {
        return cvService.retrieveMyAccountsCv(cvId)
    }

    @GetMapping("cvs")
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllMyAccountsCvs(): List<CvResponseDTO> {
        return cvService.retrieveAllMyAccountsCvs()
    }

    @GetMapping("cv")
    @ResponseStatus(HttpStatus.OK)
    fun retrieveMyAccountsLastCv(): CvResponseDTO {
        return cvService.retrieveMyAccountsLastCv()
    }

}