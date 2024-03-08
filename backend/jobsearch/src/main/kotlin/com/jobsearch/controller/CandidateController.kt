package com.jobsearch.controller

import com.jobsearch.dto.CandidateSearchFilterDTO
import com.jobsearch.repository.CandidateService
import jdk.internal.org.jline.reader.Candidate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/candidates")
class CandidateController(private val candidateService: CandidateService) {
    @GetMapping("/search")
    fun searchCandidates(@RequestBody filters: CandidateSearchFilterDTO): ResponseEntity<List<Candidate>> {
        val candidates = candidateService.searchCandidates(filters)
        return ResponseEntity.ok(candidates)
    }
}