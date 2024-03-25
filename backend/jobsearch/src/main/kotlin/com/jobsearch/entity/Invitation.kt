package com.jobsearch.entity

import jakarta.persistence.*
import java.time.*

@Entity
@Table(name = "invitations")
data class Invitation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    // Receiver
    @ManyToOne(fetch = FetchType.LAZY)  //or ManyToMany?
    @JoinColumn(name = "candidate_id")
    val candidate: User,

    // Invitation details
    var subject: String,
    var content: String,
    var sentDateTime: LocalDateTime? = null,
    var sent: Boolean = false,

    // Sender
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    val manager: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id")
    val vacancy: Vacancy,
)