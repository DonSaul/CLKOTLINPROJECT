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
        val dataBody = vacancyService.retrieveVacancy(vacancyId)
        return mapResponseEntity(dataBody)
    }

    @GetMapping()
    fun retrieveAllVacancy(): ResponseEntity<StandardResponse<List<VacancyResponseDTO>>>  {
        val dataBodylist = vacancyService.retrieveAllVacancy()
        return mapResponseEntity(dataBodylist)
    }

    @GetMapping("/search")
    fun searchVacancies(
        @RequestParam(required = false) salary: Int?,
        @RequestParam(required = false) jobFamilyId: Int?,
        @RequestParam(required = false) yearsOfExperience: Int?
    ): ResponseEntity<StandardResponse<List<VacancyResponseDTO>>>  {
        val dataBodyList = vacancyService.findVacanciesByFilter(salary, jobFamilyId, yearsOfExperience)
        return mapResponseEntity(dataBodyList)
    }

    @GetMapping("/my-vacancies")
    fun retrieveVacancyByManager(): ResponseEntity<StandardResponse<List<VacancyResponseDTO>>>  {
        val dataBodyList = vacancyService.retrieveVacancyByManager()
        return mapResponseEntity(dataBodyList)
    }
    @PostMapping
    fun createVacancy(@RequestBody @Valid vacancyDto: VacancyRequestDTO): ResponseEntity<StandardResponse<VacancyResponseDTO>> {
        val dataBody = vacancyService.createVacancy(vacancyDto)
        return mapResponseEntity(dataBody, HttpStatus.CREATED)
    }

    @PutMapping("{id}")
    fun updateVacancy(@PathVariable("id") vacancyId: Int, @Valid @RequestBody vacancyDto: VacancyRequestDTO): ResponseEntity<StandardResponse<VacancyResponseDTO>> {
        val dataBody = vacancyService.updateVacancy(vacancyId, vacancyDto)
        return mapResponseEntity(dataBody)
    }

    @DeleteMapping("{id}")
    fun deleteVacancy(@PathVariable("id") vacancyId: Int): ResponseEntity<StandardResponse<Unit>> {
        val dataBody = vacancyService.deleteVacancy(vacancyId)
        return mapResponseEntity(dataBody, HttpStatus.NO_CONTENT)
    }

    /**
     * Maps a response entity with the provided data body and status.
     * Returns a ResponseEntity of StandardResponse.
     *
     * @param dataBody The data body to be included in the response.
     * @param status The HTTP status code (default is HttpStatus.OK).
     * @return ResponseEntity<StandardResponse> The mapped response entity.
     */
    private fun <T> mapResponseEntity(
        dataBody: T,
        status: HttpStatus = HttpStatus.OK
    ): ResponseEntity<StandardResponse<T>> {
        val responseStatus = if (dataBody is List<*> && dataBody.isEmpty()) {
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