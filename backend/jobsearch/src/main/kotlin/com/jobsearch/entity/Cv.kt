package com.jobsearch.entity

import jakarta.persistence.*


@Entity
@Table(name = "cvs")
class Cv(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    var summary: String,
    var yearsOfExperience: Int,
    var salaryExpectation: Int,
    var education: String,

    @OneToMany(mappedBy = "cv", cascade = [CascadeType.ALL], orphanRemoval = true)
    val jobs: MutableSet<Job>? = mutableSetOf(),

    @OneToMany(mappedBy = "cv", cascade = [CascadeType.ALL], orphanRemoval = true)
    val projects: MutableSet<Project>? = mutableSetOf(),

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "cvs_skills",
        joinColumns = [JoinColumn(name = "cv_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "skill_id", referencedColumnName = "skillId")]
    )
    val skills: MutableSet<Skill>? = mutableSetOf(),

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "user_id")
    val user: User,
)