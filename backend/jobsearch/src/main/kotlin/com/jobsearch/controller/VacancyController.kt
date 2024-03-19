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
    // Each endpoint returns a ResponseEntity object
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
        val dataBodyList = vacancyService.findVacanciesByFilter(salary, jobFamilyId, yearsOfExperience)
        return mapResponseEntity(dataBodyList)
    }


// Manager Layer, endpoint contraints on SecurityConfiguration.kt

    @GetMapping("/my-vacancies")
    fun retrieveVacancyByManager(): ResponseEntity<StandardResponse<List<VacancyResponseDTO>>>  {
        val dataBodylist = vacancyService.retrieveVacancyByManager()
        return mapResponseEntity(dataBodylist)
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
        return mapResponseEntity(dataBody)
    }

    /**
     * Maps a response entity with the provided data body and status.
     * Returns a ResponseEntity of StandardResponse of VacancyResponseDTO.
     *
     * @param dataBody The data body to be included in the response.
     * @param status The HTTP status code (default is HttpStatus.OK).
     * @return ResponseEntity<StandardResponse<VacancyResponseDTO>> The mapped response entity.
     */
    fun mapResponseEntity(
        dataBody: VacancyResponseDTO,
        status: HttpStatus = HttpStatus.OK): ResponseEntity<StandardResponse<VacancyResponseDTO>>{

        val bodyContent = StandardResponse(
            status = status.value(),
            data = dataBody,
            message = status.reasonPhrase
        )
        return ResponseEntity
            .status(status)
            .body(bodyContent)
    }


    /**
     * Maps the response entity for a successful deletion.
     *
     * @param deletedBody The body of the response.
     * @param status The HTTP status of the response. Default is HttpStatus.NO_CONTENT.
     * @return The ResponseEntity containing the mapped response entity.
     */
    fun mapResponseEntity(
        deletedBody: Unit,
        status: HttpStatus = HttpStatus.NO_CONTENT): ResponseEntity<StandardResponse<Unit>>{

        val bodyContent = StandardResponse(
            status = status.value(),
            data = deletedBody,
            message = status.reasonPhrase
        )
        return ResponseEntity
            .status(status)
            .body(bodyContent)
    }

    /**
     * Maps a response entity with the provided data body list and status.
     * Returns a ResponseEntity of StandardResponse of VacancyResponseDTO.
     *
     * @param dataBodyList The data body list to be included in the response.
     * @param status The HTTP status code (default is HttpStatus.OK).
     * @return ResponseEntity<StandardResponse<List<VacancyResponseDTO>> The mapped response entity.
     */
    fun mapResponseEntity(
        dataBodyList: List<VacancyResponseDTO>,
        status: HttpStatus = HttpStatus.OK): ResponseEntity<StandardResponse<List<VacancyResponseDTO>>> {

        var responseStatus = status
        if (dataBodyList.isEmpty()) {
            responseStatus = HttpStatus.NO_CONTENT
        }
        val body = StandardResponse(
            status = responseStatus.value(),
            data = dataBodyList,
            message = responseStatus.reasonPhrase
        )
        return ResponseEntity
            .status(status)
            .body(body)
    }
}