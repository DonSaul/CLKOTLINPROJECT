package com.jobsearch.repository

import com.jobsearch.entity.JobFamily
import org.springframework.data.jpa.repository.JpaRepository

interface JobFamilyRepository: JpaRepository<JobFamily, Int> {
}