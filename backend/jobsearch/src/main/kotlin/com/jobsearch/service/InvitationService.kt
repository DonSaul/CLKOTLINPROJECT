package com.jobsearch.service

import com.jobsearch.dto.InvitationDTO
import com.jobsearch.entity.Invitation
import com.jobsearch.repository.InvitationRepository
import org.springframework.stereotype.Service

@Service
class InvitationService(
    val invitationRepository: InvitationRepository
) {

    fun createInvitation(invitationDTO: InvitationDTO): Any {

        val invitationEntity = InvitationDTO.let {
            Invitation(it.id, message = it.message)
        }
        invitationRepository.save()
    }



}