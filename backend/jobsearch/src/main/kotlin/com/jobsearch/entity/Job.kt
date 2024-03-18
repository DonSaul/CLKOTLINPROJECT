package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name="job")
class Job (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var startYear: Int,
    var startMonth: Int,
    var endYear: Int,
    var endMonth: Int,

    var position: String,
    var description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id")
    var cv: Cv,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_family_id")
    var jobFamily: JobFamily
)