package com.jobsearch.repository

import com.jobsearch.dto.CandidateSearchFilterDTO
import jdk.internal.org.jline.reader.Candidate

interface CandidateService {
    fun searchCandidates(filters: CandidateSearchFilterDTO): List<Candidate>
}
