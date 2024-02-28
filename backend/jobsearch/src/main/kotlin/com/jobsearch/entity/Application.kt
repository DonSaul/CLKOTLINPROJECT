package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name = "applications")
data class Application(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    val applicationId: Int? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val candidate: User,

    @ManyToOne
    @JoinColumn(name = "cv_id")
    val cv: Cv,

    @ManyToOne
    @JoinColumn(name = "vacancy_id")
    val vacancy: Vacancy,

    @Column(name = "application_status")
    var applicationStatus: String
)
