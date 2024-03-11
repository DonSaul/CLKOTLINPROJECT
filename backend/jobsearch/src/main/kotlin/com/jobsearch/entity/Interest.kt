package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name = "interest")
data class Interest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "jobFamily_id")
    val jobFamily : JobFamily,

    //Still discussing what relation use (many-to-many or many-to-one)
    @ManyToOne
    @JoinColumn(name = "userId")
    val user: User
)