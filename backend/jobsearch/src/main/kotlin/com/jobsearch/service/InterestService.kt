package com.jobsearch.service

import com.jobsearch.entity.Interest
import com.jobsearch.entity.JobFamily
import com.jobsearch.entity.User
import com.jobsearch.repository.InterestRepository
import com.jobsearch.repository.JobFamilyRepository
import com.jobsearch.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InterestService(
    private val interestRepository: InterestRepository,
    private val userRepository: UserRepository,
    private val jobFamilyRepository: JobFamilyRepository
) {
    fun getUsersByJobFamilyId(jobFamilyId: Int): List<User> {
        val interests = interestRepository.findUsersByJobFamilyId(jobFamilyId)

        val users = interests.map {it}

        return users.distinct()
    }

    fun createInterest(jobFamilyId: Int, userId: Int) {
        val user = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("No user found with id $userId") }

        val jobFamily = jobFamilyRepository.findById(jobFamilyId)
            .orElseThrow { NoSuchElementException("No job family found with id $jobFamilyId") }

            val interest = Interest(jobFamily = jobFamily, user = user)
            interestRepository.save(interest)

    }

    @Transactional
    fun deleteInterestByUserIdAndJobFamilyId(jobFamilyId: Int, userId: Int) {
        interestRepository.deleteInterestsByUserIdAndJobFamilyId(userId, jobFamilyId)
    }
    fun updateInterest(jobFamilyId: Int, userId: Int) {
        val existingInterests = interestRepository.findInterestsByUserId(userId)

        val matchingInterest = existingInterests.find { it.jobFamily.id == jobFamilyId }


        if (matchingInterest != null) {
            matchingInterest.jobFamily.id = jobFamilyId
            interestRepository.save(matchingInterest)
        }
    }

    fun getJobFamilyByUserId(userId: Int): List<JobFamily> {
        val interests = interestRepository.getJobFamilyByUserId(userId)
        return interests
    }
}

