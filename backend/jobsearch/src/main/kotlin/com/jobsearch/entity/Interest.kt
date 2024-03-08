package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name = "interest")
data class Interest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @OneToMany
    @JoinColumn(name = "jobFamily_id")
    val jobFamily : JobFamily,

    //Still discussing what relation use (many-to-many or one-to-many)
    @OneToMany
    @JoinColumn(name = "userId")
    val user: User
)