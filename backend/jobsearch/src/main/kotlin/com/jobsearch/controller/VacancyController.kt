package com.jobsearch.controller

import com.jobsearch.dto.VacancyRequestDTO
import com.jobsearch.dto.VacancyResponseDTO
import com.jobsearch.service.VacancyService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/vacancy")
@Validated
class VacancyController(
    val vacancyService: VacancyService
) {
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun retrieveVacancy(@PathVariable("id") vacancyId: Int): VacancyResponseDTO {
        return vacancyService.retrieveVacancy(vacancyId)
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllVacancy(): List<VacancyResponseDTO> {
        return vacancyService.retrieveAllVacancy()
    }

    @GetMapping("/search")
    fun searchVacancies(
        @RequestParam(required = false) salary: Int?,
        @RequestParam(required = false) jobFamilyId: Int?,
        @RequestParam(required = false) yearsOfExperience: Int?
    ): List<VacancyResponseDTO> {
        return vacancyService.findVacanciesByFilter(salary, jobFamilyId, yearsOfExperience)
    }


// Manager Layer, endpoint contraints on SecurityCOnfiguration.kt

    @GetMapping("/manage")
    @ResponseStatus(HttpStatus.OK)
    fun retrieveVacancyByManager(): List<VacancyResponseDTO> {
        return vacancyService.retrieveVacancyByManager()
    }
    @PostMapping
    @PreAuthorize("hasAuthorities('manager')")
    @ResponseStatus(HttpStatus.CREATED)
    fun createVacancy(@RequestBody @Valid vacancyDto: VacancyRequestDTO): VacancyResponseDTO {
        return vacancyService.createVacancy(vacancyDto)
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateVacancy(@PathVariable("id") vacancyId: Int, @Valid @RequestBody vacancyDto: VacancyRequestDTO): VacancyResponseDTO {
        return vacancyService.updateVacancy(vacancyId, vacancyDto)
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteVacancy(@PathVariable("id") vacancyId: Int) {
        return vacancyService.deleteVacancy(vacancyId)
    }
}