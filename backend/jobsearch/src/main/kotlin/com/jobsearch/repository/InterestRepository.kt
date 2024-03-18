package com.jobsearch.repository

import com.jobsearch.entity.Interest
import com.jobsearch.entity.JobFamily
import com.jobsearch.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface InterestRepository : JpaRepository<Interest, Int> {
    @Query("SELECT i.user FROM Interest i WHERE i.jobFamily.id = :jobFamilyId")
    fun findUsersByJobFamilyId(jobFamilyId: Int): List<User>
    @Query("SELECT i.jobFamily FROM Interest i WHERE i.user.id = :userId")
    fun getJobFamilyByUserId(userId: Int): List<JobFamily>

    fun deleteByUserId(userId: Int)

    @Transactional
    fun deleteInterestsByUserIdAndJobFamilyId(jobFamilyId: Int, userId: Int)

    fun findInterestsByUserId(userId: Int): List<Interest>
}