package com.jobsearch.controller

import com.jobsearch.dto.InvitationDTO
import com.jobsearch.service.InvitationService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/invitations")
class InvitationController(invitationService: InvitationService)//: InvitationDTO
{
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createInvitation(@RequestBody invitationDTO: InvitationDTO){
        //return
    }
}