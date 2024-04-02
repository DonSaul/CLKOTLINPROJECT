package com.jobsearch.repository

import com.jobsearch.entity.User
import com.jobsearch.entity.Vacancy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface VacancyRepository: JpaRepository<Vacancy, Int> {
    /**
     * Finds vacancies based on filters.
     *
     * @param salary The minimum salary expectation.
     * @param jobFamilyId The ID of the job family.
     * @param yearsOfExperience The minimum years of experience.
     * @return A list of vacancies that match the filters.
     */
    @Query("SELECT v FROM Vacancy v WHERE " +
            "(:salary IS NULL OR v.salaryExpectation >= :salary) " +
            "AND (:jobFamilyId IS NULL OR v.jobFamily.id = :jobFamilyId) " +
            "AND (:yearsOfExperience IS NULL OR v.yearsOfExperience >= :yearsOfExperience)")
    fun findVacanciesByFilters(salary: Int?, jobFamilyId: Int?, yearsOfExperience: Int?): List<Vacancy>
    /**
     * Finds vacancies filtered by the given manager.
     *
     * @param manager the manager to find vacancies for
     * @return a list of vacancies filtered by the given manager
     */
    fun findByManager(manager: User): List<Vacancy>

    /**
     * Checks if a vacancy with the given ID exists.
     *
     * @param id the ID of the vacancy to check
     * @return true if the vacancy exists, false otherwise
     */
    override fun existsById(id: Int): Boolean

}
