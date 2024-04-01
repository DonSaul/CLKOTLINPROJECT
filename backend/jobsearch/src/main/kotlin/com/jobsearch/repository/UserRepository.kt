package com.jobsearch.repository

import com.jobsearch.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository : JpaRepository<User, Int> {
    fun findByEmail(email: String): Optional<User>
    fun findByResetPasswordToken(token: String): Optional<User>

    @Query("""
    SELECT v.user FROM Cv v 
    WHERE v.id IN (
        SELECT MAX(v2.id) 
        FROM Cv v2 
        GROUP BY v2.user
    ) 
    AND (
        EXISTS (
            SELECT j.id FROM Job j WHERE j.cv = v AND j.jobFamily.id = :jobFamilyId
        ) 
        OR EXISTS (
            SELECT p.id FROM Project p WHERE p.cv = v AND p.jobFamily.id = :jobFamilyId
        )
    )
    ORDER BY v.id DESC
    """)
    fun findUsersByJobFamily(jobFamilyId: Int): List<User>


}
