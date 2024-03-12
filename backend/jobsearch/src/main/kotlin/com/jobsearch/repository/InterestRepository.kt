package com.jobsearch.repository

import com.jobsearch.entity.Interest
import com.jobsearch.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface InterestRepository : JpaRepository<Interest, Int> {
    @Query("SELECT i.user FROM Interest i WHERE i.jobFamily.id = :jobFamilyId")
    fun findUsersByJobFamilyId(jobFamilyId: Int): List<User>
}