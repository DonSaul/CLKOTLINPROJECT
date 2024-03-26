package com.jobsearch.repository

import com.jobsearch.entity.Invitation
import org.springframework.data.jpa.repository.JpaRepository

interface InvitationRepository: JpaRepository<Invitation, Int>