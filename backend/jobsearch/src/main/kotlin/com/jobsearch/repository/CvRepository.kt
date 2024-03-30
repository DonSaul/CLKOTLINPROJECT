package com.jobsearch.repository

import com.jobsearch.entity.Cv
import com.jobsearch.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CvRepository : JpaRepository<Cv, Int> {
    fun findByUser(user: User): List<Cv>

    fun findByUserAndId(user: User, id: Int): Optional<Cv>

    fun findFirstByUserOrderByIdDesc(user: User): Optional<Cv>

    @Query("""
    SELECT v FROM Cv v 
    WHERE v.id IN (
        SELECT MAX(v2.id) 
        FROM Cv v2 
        GROUP BY v2.user
    ) 
    AND (:salary IS NULL OR v.salaryExpectation >= :salary) 
    AND (:yearsOfExperience IS NULL OR v.yearsOfExperience >= :yearsOfExperience)
    AND (
        :jobFamilyId IS NULL 
        OR EXISTS (
            SELECT j.id FROM Job j WHERE j.cv = v AND j.jobFamily.id = :jobFamilyId
        ) 
        OR EXISTS (
            SELECT p.id FROM Project p WHERE p.cv = v AND p.jobFamily.id = :jobFamilyId
        )
    )
    ORDER BY v.id DESC
    """)
    fun findCvByFilter(salary: Int?, jobFamilyId: Int?, yearsOfExperience: Int?): List<Cv>

}