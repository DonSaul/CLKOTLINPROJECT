package com.jobsearch.repository

import com.jobsearch.entity.User
import com.jobsearch.entity.Vacancy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface VacancyRepository: JpaRepository<Vacancy, Int> {
    @Query("SELECT v FROM Vacancy v WHERE " +
            "(:salary IS NULL OR v.salaryExpectation >= :salary) " +
            "AND (:jobFamilyId IS NULL OR v.jobFamily.id = :jobFamilyId) " +
            "AND (:yearsOfExperience IS NULL OR v.yearsOfExperience >= :yearsOfExperience)")
    fun findVacanciesByFilters(salary: Int?, jobFamilyId: Int?, yearsOfExperience: Int?): List<Vacancy>

    fun findByManager(manager: User): List<Vacancy>
}
