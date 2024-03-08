package com.jobsearch.entity

import jakarta.persistence.*
import java.time.*

@Entity
@Table(name = "invitations")
data class Invitation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val message: String,
    val time: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "manager_id")
    val manager: User,

    @ManyToOne  //or ManyToMany?
    @JoinColumn(name = "candidate_id")
    val candidate: User,

    @ManyToOne
    @JoinColumn(name = "vacancy_id")
    val vacancy: Vacancy,
)