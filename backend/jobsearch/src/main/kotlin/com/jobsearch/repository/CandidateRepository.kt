package com.jobsearch.repository

import com.jobsearch.dto.Candidate
import com.jobsearch.dto.CandidateSearchFilterDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CandidateRepository: JpaRepository<Candidate, Long> {
    @Query("SELECT c FROM Candidate c WHERE (:#{#filters.yearsOfExperience} IS NULL OR c.yearsOfExperience = :#{#filters.yearsOfExperience}) " +
            "AND (:#{#filters.jobFamily} IS NULL OR c.jobFamily = :#{#filters.jobFamily}) " +
            "AND (:#{#filters.salaryExpectation} IS NULL OR c.salaryExpectation = :#{#filters.salaryExpectation})")
    fun searchCandidates(filters: CandidateSearchFilterDTO): List<Candidate>
}