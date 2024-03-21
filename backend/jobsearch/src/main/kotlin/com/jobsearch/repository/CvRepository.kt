package com.jobsearch.repository

import com.jobsearch.entity.Cv
import com.jobsearch.entity.User
import com.jobsearch.entity.Vacancy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CvRepository : JpaRepository<Cv, Int> {
    fun findByUser(user: User): List<Cv>

    fun findByUserAndId(user: User, id: Int): Cv

    fun findFirstByUserOrderByIdDesc(user: User): Cv

    @Query("SELECT v FROM Cv v WHERE v.id IN (SELECT MAX(v2.id) FROM Cv v2 GROUP BY v2.user) " +
            "AND (:salary IS NULL OR v.salaryExpectation >= :salary) " +
            "AND (:yearsOfExperience IS NULL OR v.yearsOfExperience >= :yearsOfExperience) " +
            "ORDER BY v.id DESC")
    fun findCvByFilter(salary: Int?, yearsOfExperience: Int?): List<Cv>
}
