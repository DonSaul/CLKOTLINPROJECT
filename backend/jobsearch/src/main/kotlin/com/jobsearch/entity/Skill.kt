package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name="Skill")
data class Skill (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val skillId:Int?=null,
        val name : String
)
