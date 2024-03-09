package com.jobsearch.service

import com.jobsearch.dto.Candidate
import com.jobsearch.dto.CandidateSearchFilterDTO
import com.jobsearch.repository.CandidateRepository
import com.jobsearch.repository.CandidateService
import org.springframework.stereotype.Service

@Service
class CandidateServiceImpl(private val candidateRepository: CandidateRepository) : CandidateService {

    override fun searchCandidates(filters: CandidateSearchFilterDTO): List<Candidate> {
        return candidateRepository.searchCandidates(filters)
    }
}    