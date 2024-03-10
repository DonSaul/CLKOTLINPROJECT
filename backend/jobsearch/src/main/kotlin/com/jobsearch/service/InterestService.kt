package com.jobsearch.service

import com.jobsearch.entity.User
import com.jobsearch.repository.InterestRepository
import org.springframework.stereotype.Service

@Service
class InterestService(
    private val interestRepository: InterestRepository
) {
    fun getUsersByJobFamilyId(jobFamilyId: Int): List<User> {
        val interests = interestRepository.findUsersByJobFamilyId(jobFamilyId)

        val users = interests.map {it}

        return users.distinct()
    }
}

