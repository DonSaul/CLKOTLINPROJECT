package com.jobsearch.repository

import com.jobsearch.entity.Status
import org.springframework.data.jpa.repository.JpaRepository

interface StatusRepository: JpaRepository<Status, Int>