package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name = "cv_has_project")
data class CVHasProject(
        //This is not done, CV missing
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val cvHasProjectId: Long? = null,

        //@ManyToOne
        //@JoinColumn(name = "cv_id")
        //val cv: Cv,

        @ManyToOne
        @JoinColumn(name = "project_id")
        val project: Project
)