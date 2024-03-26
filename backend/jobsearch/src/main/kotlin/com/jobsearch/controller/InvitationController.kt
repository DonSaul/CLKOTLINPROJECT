package com.jobsearch.controller

import com.jobsearch.dto.InvitationDTO
import com.jobsearch.service.InvitationService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/invitations")
class InvitationController(val invitationService: InvitationService) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createInvitation(@RequestBody invitationDTO: InvitationDTO): InvitationDTO {
        return invitationService.createInvitation(invitationDTO)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllInvitations(): List<InvitationDTO> {
        return invitationService.retrieveAllInvitations()
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun retrieveInvitation(@PathVariable("id") invitationId: Int): InvitationDTO {
        return invitationService.retrieveInvitation(invitationId)
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateInvitation(@PathVariable("id") invitationId: Int, @RequestBody invitationDTO: InvitationDTO): InvitationDTO {
        return invitationService.updateInvitation(invitationDTO)
    }


    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteInvitation(@PathVariable("id") invitationId: Int): String {
        return invitationService.deleteInvitation(invitationId)
    }
}