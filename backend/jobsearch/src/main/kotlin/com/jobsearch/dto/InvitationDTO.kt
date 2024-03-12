package com.jobsearch.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PastOrPresent
import java.time.*

class InvitationDTO(
    val id: Int?,
    @get:NotBlank(message = "Message must not be blank")
    val message: String,
    @get:PastOrPresent(message = "Time must be valid")
    val time: LocalDateTime = LocalDateTime.now(),
    val managerId: Int?,
    val candidateId: Int?,
    val vacancyId: Int?,
)

