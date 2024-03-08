package com.jobsearch.dto

import java.time.*

class InvitationDTO(
    val id: Int?,
    val message: String,
    val time: LocalDateTime = LocalDateTime.now(),
    val managerId: Int,
    val candidateId: Int,
    val vacancyId: Int,
)

