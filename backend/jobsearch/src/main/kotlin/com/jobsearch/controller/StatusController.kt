package com.jobsearch.controller

import com.jobsearch.dto.StatusDTO
import com.jobsearch.service.StatusService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/application-status")
class StatusController(val statusService: StatusService) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createStatus(@Valid @RequestBody statusDTO: StatusDTO): StatusDTO {
        return statusService.createStatus(statusDTO)
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun retrieveStatus(@PathVariable("id") statusId: Int): StatusDTO {
        return statusService.retrieveStatus(statusId)
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllStatus(): List<StatusDTO> {
        return statusService.retrieveAllStatus()
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateStatus(@PathVariable("id") statusId: Int,@Valid @RequestBody statusDTO: StatusDTO): StatusDTO {
        return statusService.updateStatus(statusId, statusDTO)
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteStatus(@PathVariable("id") statusId: Int): String {
        return statusService.deleteStatus(statusId)
    }
}