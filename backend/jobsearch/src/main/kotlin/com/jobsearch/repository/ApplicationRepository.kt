package com.jobsearch.repository

import com.jobsearch.entity.Application
import org.springframework.data.jpa.repository.JpaRepository

interface ApplicationRepository: JpaRepository<Application, Int> {
}