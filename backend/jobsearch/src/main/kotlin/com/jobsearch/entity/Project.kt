package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name="project")
data class Project (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        var name: String,
        @Column(columnDefinition = "TEXT")
        var description: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "cv_id")
        var cv: Cv,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "job_family_id")
        var jobFamily: JobFamily
)