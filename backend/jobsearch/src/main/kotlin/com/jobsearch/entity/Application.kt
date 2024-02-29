package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name = "applications")
data class Application(

        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    val id: Int? = null,

        @ManyToOne
    @JoinColumn(name = "user_id")
    val candidate: User,

        @ManyToOne
    @JoinColumn(name = "cv_id")
    val cv: Cv,

        @ManyToOne
    @JoinColumn(name = "vacancy_id")
    val vacancy: Vacancy,

        @ManyToOne
        @JoinColumn(name = "status_id")
    var applicationStatus: Status
)
