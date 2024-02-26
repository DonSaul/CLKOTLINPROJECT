package com.jobsearch.repository

import com.jobsearch.entity.Vacancy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface VacancyRepository: JpaRepository<Vacancy, Int> {
    @Query(value = "SELECT * FROM vacancy WHERE job_family_id = ?1", nativeQuery = true)
    fun findByJobFamilyId(jobFamilyId: Int): List<Vacancy>
    @Query(value = "SELECT * FROM vacancy WHERE salary_expectation >= ?1", nativeQuery = true)
    fun findBySalaryExpectation(salary: Int): List<Vacancy>

    @Query(value = "SELECT * FROM vacancy WHERE years_of_experience >= ?1", nativeQuery = true)
    fun findByYearsOfExperience(yearsOfExperience: Int): List<Vacancy>
}
