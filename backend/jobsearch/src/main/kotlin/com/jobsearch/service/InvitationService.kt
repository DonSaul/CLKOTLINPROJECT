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
    val notificationService: NotificationService
) {
    @Transactional
    fun createInvitation(invitationDTO: InvitationDTO): InvitationDTO {
        // Check if an invitation for the same candidate and vacancy already exists
        val alreadyInvited = invitationRepository.findByCandidateIdAndVacancyId(
            invitationDTO.candidateId!!,
            invitationDTO.vacancyId!!
        )

        if (alreadyInvited != null) {
            throw RuntimeException("Invitation for this vacancy already sent to this candidate")
        }

        val managerUser = userService.retrieveAuthenticatedUser()

        val vacancy = vacancyRepository.findById(invitationDTO.vacancyId!!)
            .orElseThrow { NotFoundException("Vacancy not found with ID: ${invitationDTO.vacancyId}") }

        val candidateId = invitationDTO.candidateId
        val candidate = userRepository.findById(candidateId!!)
            .orElseThrow { NotFoundException("Candidate not found with ID: $candidateId") }

        val MAX_CONTENT_LENGTH = 255

        val vacancyLink = "Follow this link to view the vacancy: http://localhost:3000/vacancies/${vacancy.id}"
        val content = "${invitationDTO.content}. $vacancyLink"
        val truncatedContent = if (content.length > MAX_CONTENT_LENGTH) {
            content.substring(0, MAX_CONTENT_LENGTH)
        } else {
            content
        }

        val currentDateTime = LocalDateTime.now()

        val invitationEntity = invitationDTO.let {
            Invitation(it.id, candidate, it.subject, truncatedContent, currentDateTime, it.sent, managerUser, vacancy)
        }

        val newInvitation = invitationRepository.save(invitationEntity)

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
    }

    fun createMultipleInvitation(invitationDTO: InvitationDTO): InvitationDTO {
        if (invitationDTO.candidateIds.isNullOrEmpty()) {
            createInvitation(invitationDTO)
        } else {
            invitationDTO.candidateIds?.forEach {candidateId ->
                val newInvitation = invitationDTO.copy(candidateId = candidateId)
                createInvitation(newInvitation)
            }
        }
        return invitationDTO
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
                listOf(),
                it.subject,
                it.content,
                it.sentDateTime,
                it.sent,
                it.manager.id!!,
                it.vacancy.id!!,
            )
        }
    }
}

