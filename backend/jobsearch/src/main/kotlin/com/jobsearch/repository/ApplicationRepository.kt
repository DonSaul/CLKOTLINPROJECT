package com.jobsearch.repository

import com.jobsearch.entity.Application
import com.jobsearch.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface ApplicationRepository: JpaRepository<Application, Int> {

    fun findByCandidate(candidate: User): List<Application>
}