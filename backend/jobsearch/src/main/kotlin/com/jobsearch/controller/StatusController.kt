package com.jobsearch.controller

import com.jobsearch.dto.StatusDTO
import com.jobsearch.service.StatusService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/application-status")
class StatusController(val statusService: StatusService) {
    @PostMapping
    fun createStatus(@RequestBody statusDTO: StatusDTO): StatusDTO {
        return statusService.createStatus(statusDTO)
    }

    @GetMapping("{id}")
    fun retrieveStatus(@PathVariable("id") statusId: Int): StatusDTO {
        return statusService.retrieveStatus(statusId)
    }

    @GetMapping()
    fun retrieveAllJobFamilies(): List<StatusDTO> {
        return statusService.retrieveAllStatus()
    }

    @PutMapping("{id}")
    fun updateStatus(@PathVariable("id") statusId: Int, @RequestBody statusDTO: StatusDTO): StatusDTO {
        return statusService.updateStatus(statusId, statusDTO)
    }

    @DeleteMapping("{id}")
    fun deleteStatus(@PathVariable("id") statusId: Int): String {
        return statusService.deleteStatus(statusId)
    }
}