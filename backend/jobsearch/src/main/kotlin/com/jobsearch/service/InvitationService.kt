package com.jobsearch.service

import com.jobsearch.dto.ApplicationDTO
import com.jobsearch.dto.InvitationDTO
import com.jobsearch.entity.Application
import com.jobsearch.entity.Invitation
import com.jobsearch.entity.User
import com.jobsearch.entity.Vacancy
import com.jobsearch.exception.NotFoundException
import com.jobsearch.repository.InvitationRepository
import com.jobsearch.repository.UserRepository
import com.jobsearch.repository.VacancyRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class InvitationService(
    val invitationRepository: InvitationRepository,
    val userService: UserService,
    val userRepository: UserRepository,
    val vacancyRepository: VacancyRepository
) {
    @Transactional
    fun createInvitation(invitationDTO: InvitationDTO): InvitationDTO {

       val managerUser = userService.retrieveAuthenticatedUser()

//        val managerId = invitationDTO.managerId
//        val managerUser = userRepository.findById(managerId!!)
//            .orElseThrow { NotFoundException("Manager not found with ID: $managerId") }

        val vacancy = vacancyRepository.findById(invitationDTO.vacancyId!!)
            .orElseThrow { NotFoundException("Vacancy not found with ID: ${invitationDTO.vacancyId}") }

        val candidateId = invitationDTO.candidateId
        val candidate = userRepository.findById(candidateId!!)
            .orElseThrow { NotFoundException("Candidate not found with ID: $candidateId") }

        val currentDateTime = LocalDateTime.now()

        val invitationEntity = invitationDTO.let {
            Invitation(it.id, it.message, currentDateTime, managerUser, candidate, vacancy)
        }

        val newInvitation = invitationRepository.save(invitationEntity)

        return mapToInvitationDTO(newInvitation)
        
//        if (invitationAlreadySent(invitationEntity)) {
//            throw RuntimeException("Invitation already sent to this candidate")
//        } else {

//        val newInvitation = invitationEntity.let { invitationRepository.save(it) }
//
//        return mapToInvitationDTO(newInvitation)
    }

    fun retrieveAllInvitations(): List<InvitationDTO> {
        val invitation = invitationRepository.findAll()
        return invitation.map {
            mapToInvitationDTO(it)
        }
    }

    fun retrieveInvitation(invitationId: Int): InvitationDTO {
        val invitation = invitationRepository.findById(invitationId)
            .orElseThrow { NotFoundException("No invitation found with $invitationId") }
        return mapToInvitationDTO(invitation)
    }

    @Transactional
    fun updateInvitation(invitationDTO: InvitationDTO): InvitationDTO {
        val invitation = invitationRepository.findById(invitationDTO.id!!)
            .orElseThrow { NotFoundException("No invitation found with id ${invitationDTO.id}") }

        invitation.message = invitationDTO.message
        // preguntar si se pueded modificar vacancy

        val updatedInvitation = invitationRepository.save(invitation)
        return mapToInvitationDTO(updatedInvitation)
    }

    @Transactional
    fun deleteInvitation(invitationId: Int) : String {
        val invitation = invitationRepository.findById(invitationId)
            .orElseThrow{ NotFoundException("No invitation found with id $invitationId") }

        invitationRepository.delete(invitation)

        return "Invitation Deleted Successfully"
    }

    fun mapToInvitationDTO(invitation: Invitation): InvitationDTO {
        return invitation.let {
            InvitationDTO(
                it.id,
                it.message,
                it.time,
                it.manager.id!!,
                it.candidate.id!!,
                it.vacancy.id!!,
            )
        }
    }

//    fun invitationAlreadySent(invitationEntity: Invitation): Boolean {
//        val candidateInvitationList = invitationRepository.findById(invitationEntity.candidate.id!!)
//        for (invitation in candidateInvitationList){
//            if (invitation.vacancy == invitationEntity.vacancy){
//                return true
//            }
//        }
//        return false
//    }

}