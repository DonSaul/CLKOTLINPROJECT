package com.jobsearch.repository

import com.jobsearch.dto.Candidate
import com.jobsearch.dto.CandidateSearchFilterDTO

interface CandidateRepository {
    fun searchCandidates(filters: CandidateSearchFilterDTO): List<Candidate>
}