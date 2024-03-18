package com.jobsearch.controller

import com.jobsearch.dto.VacancyRequestDTO
import com.jobsearch.dto.VacancyResponseDTO
import com.jobsearch.response.StandardResponse
import com.jobsearch.service.VacancyService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/vacancy")
@Validated
class VacancyController(
    val vacancyService: VacancyService
) {
    @GetMapping("{id}")
    fun retrieveVacancy(@PathVariable("id") vacancyId: Int): ResponseEntity<StandardResponse<VacancyResponseDTO>> {
        val responseEntity = vacancyService.retrieveVacancy(vacancyId)
        val status = HttpStatus.OK
        val body = StandardResponse(
            status = status.value(),
            data = responseEntity
        )
        return ResponseEntity
            .status(status)
            .body(body)
    }

    @GetMapping()
    fun retrieveAllVacancy(): ResponseEntity<StandardResponse<List<VacancyResponseDTO>>>  {
        val responseEntityList = vacancyService.retrieveAllVacancy()
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

    @GetMapping("/search")
    fun searchVacancies(
        @RequestParam(required = false) salary: Int?,
        @RequestParam(required = false) jobFamilyId: Int?,
        @RequestParam(required = false) yearsOfExperience: Int?
    ): ResponseEntity<StandardResponse<List<VacancyResponseDTO>>>  {
        val responseEntityList = vacancyService.findVacanciesByFilter(salary, jobFamilyId, yearsOfExperience)
        val status = HttpStatus.OK
//        if (responseEntityList.isEmpty()) {
//            status = HttpStatus.NO_CONTENT
//        }
        val body = StandardResponse(
            status = status.value(),
            data = responseEntityList
        )
        return ResponseEntity
            .status(status)
            .body(body)
    }


// Manager Layer, endpoint contraints on SecurityCOnfiguration.kt

    @GetMapping("/my-vacancies")
    fun retrieveVacancyByManager(): ResponseEntity<StandardResponse<List<VacancyResponseDTO>>>  {
        val responseEntityList = vacancyService.retrieveVacancyByManager()
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
    @PostMapping
    fun createVacancy(@RequestBody @Valid vacancyDto: VacancyRequestDTO): ResponseEntity<StandardResponse<VacancyResponseDTO>> {
        val responseEntity = vacancyService.createVacancy(vacancyDto)
        val status = HttpStatus.CREATED
        val body = StandardResponse(
            status = status.value(),
            data = responseEntity
        )
        return ResponseEntity
            .status(status)
            .body(body)
    }

    @PutMapping("{id}")
    fun updateVacancy(@PathVariable("id") vacancyId: Int, @Valid @RequestBody vacancyDto: VacancyRequestDTO): ResponseEntity<StandardResponse<VacancyResponseDTO>> {
        val responseEntity = vacancyService.updateVacancy(vacancyId, vacancyDto)
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
    fun deleteVacancy(@PathVariable("id") vacancyId: Int) {
        return vacancyService.deleteVacancy(vacancyId)
    }
}