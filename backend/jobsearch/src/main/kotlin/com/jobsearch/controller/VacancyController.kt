package com.jobsearch.controller

import com.jobsearch.dto.VacancyDto
import com.jobsearch.service.VacancyService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/vacancy")
@Validated
class VacancyController(
    val vacancyService: VacancyService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createVacancy(@RequestBody @Valid vacancyDto: VacancyDto): VacancyDto {
        return vacancyService.createVacancy(vacancyDto)
    }

    @GetMapping("{id}")
    fun retrieveVacancy(@PathVariable("id") vacancyId: Int): VacancyDto {
        return vacancyService.retrieveVacancy(vacancyId)
    }

    @GetMapping()
    fun retrieveAllVacancy(): List<VacancyDto> {
        return vacancyService.retrieveAllVacancy()
    }

    @PutMapping("{id}")
    fun updateVacancy(@PathVariable("id") vacancyId: Int, @RequestBody vacancyDto: VacancyDto): VacancyDto {
        return vacancyService.updateVacancy(vacancyId, vacancyDto)
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteVacancy(@PathVariable("id") vacancyId: Int): String {
        return vacancyService.deleteVacancy(vacancyId)
    }

}