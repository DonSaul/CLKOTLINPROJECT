package com.jobsearch.service

import com.jobsearch.dto.InvitationDTO
import com.jobsearch.dto.NotificationDTO
import com.jobsearch.entity.Invitation
import com.jobsearch.entity.NotificationTypeEnum
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
    val vacancyRepository: VacancyRepository,
//    val vacancyService: VacancyService,
    val notificationService: NotificationService
) {
    @Transactional
    fun createInvitation(invitationDTO: InvitationDTO): InvitationDTO {

        val managerUser = userService.retrieveAuthenticatedUser()


        val vacancy = vacancyRepository.findById(invitationDTO.vacancyId!!)
            .orElseThrow { NotFoundException("Vacancy not found with ID: ${invitationDTO.vacancyId}") }

        val candidateId = invitationDTO.candidateId
        val candidate = userRepository.findById(candidateId!!)
            .orElseThrow { NotFoundException("Candidate not found with ID: $candidateId") }

        val currentDateTime = LocalDateTime.now()

//        val defaultSubject = "Default Subject"
//        val defaultContent = "Default content"

        val invitationEntity = invitationDTO.let {
            Invitation(it.id, candidate, it.subject, it.content, currentDateTime, it.sent, managerUser, vacancy)
        }

        val newInvitation = invitationRepository.save(invitationEntity)

//        sendInvitation(newInvitation)

        // Get notification when sent
        val notificationDTO = NotificationDTO(
            type = NotificationTypeEnum.INVITATIONS.id,
            recipient = newInvitation.candidate.id!!,
            subject = newInvitation.subject,
            content = newInvitation.content,
            sender = newInvitation.manager.id,
            vacancy = newInvitation.vacancy.id,
        )
        notificationService.triggerNotification(notificationDTO)



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

        invitation.subject = invitationDTO.subject
        invitation.content = invitationDTO.content

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
                it.candidate.id!!,
                it.subject,
                it.content,
                it.sentDateTime,
                it.sent,
                it.manager.id!!,
                it.vacancy.id!!,
            )
        }
    }

//    fun sendInvitation(invitation: Invitation) {
//        invitation.sent = true
//        //invitation.sentDateTime = LocalDateTime.now()
//
//        invitationRepository.save(invitation)
//    }
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

