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
@RequestMapping("/api/v1/cvs")
@Validated
class CvController(
    val cvService: CvService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCv(@RequestBody @Valid cvRequestDTO: CvRequestDTO): CvResponseDTO {
        return cvService.createCv(cvRequestDTO)
    }

    @GetMapping("{id}")
    fun retrieveVacancy(@PathVariable("id") cvId: Int): CvResponseDTO {
        return cvService.retrieveCv(cvId)
    }

    @GetMapping
    fun retrieveAllCvs(): List<CvResponseDTO> {
        return cvService.retrieveAllCvs()
    }

    @PutMapping("{id}")
    fun updateCv(@PathVariable("id") cvId: Int, @RequestBody cvRequestDTO: CvRequestDTO): CvResponseDTO {
        return cvService.updateCv(cvId, cvRequestDTO)
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCv(@PathVariable("id") vacancyId: Int): String {
        return cvService.deleteCv(vacancyId)
    }

}