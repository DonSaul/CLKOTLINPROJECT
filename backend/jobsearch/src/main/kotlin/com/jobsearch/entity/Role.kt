package com.jobsearch.entity
import jakarta.persistence.*
@Entity
@Table(name = "Roles")
data class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val roleId : Int?,
    var name: String

}
