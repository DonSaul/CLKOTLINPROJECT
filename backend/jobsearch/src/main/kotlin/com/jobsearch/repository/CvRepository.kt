package com.jobsearch.repository

import com.jobsearch.entity.Cv
import com.jobsearch.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CvRepository: JpaRepository<Cv, Int> {
    fun findByUser(user: User): List<Cv>

    fun findByUserAndId(user: User, id: Int): Cv

    fun findFirstByUserOrderByIdDesc(user: User): Cv
}