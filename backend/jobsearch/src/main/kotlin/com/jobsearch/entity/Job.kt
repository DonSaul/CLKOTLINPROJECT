package com.jobsearch.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name="job")
class Job (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var startDate: LocalDate,
    var endDate: LocalDate,

    var company: String,
    var position: String,
    @Column(columnDefinition = "TEXT")
    var description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id")
    var cv: Cv,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_family_id")
    var jobFamily: JobFamily
)