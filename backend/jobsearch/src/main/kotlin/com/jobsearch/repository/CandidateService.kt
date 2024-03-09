package com.jobsearch.repository

import com.jobsearch.dto.Candidate
import com.jobsearch.dto.CandidateSearchFilterDTO

interface CandidateService {
    fun searchCandidates(filters: CandidateSearchFilterDTO): List<Candidate>
}
