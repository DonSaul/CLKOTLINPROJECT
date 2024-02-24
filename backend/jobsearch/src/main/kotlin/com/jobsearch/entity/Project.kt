package com.jobsearch.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name="project")
data class Project (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val projectId:Int?=null,
        val name:String,
        val description:String

)