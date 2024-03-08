package com.jobsearch.entity

import jakarta.persistence.*

@Entity
@Table(name="project")
class Job (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var jobId:Int?=null,
        var startDate:String,
        var endDate:String,
        var position:String,
        var description:String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "cv_id")
        var cv: Cv,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "job_family_id")
        var jobFamily: JobFamily
)