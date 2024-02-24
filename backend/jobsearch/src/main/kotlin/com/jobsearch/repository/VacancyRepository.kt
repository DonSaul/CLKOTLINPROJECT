package com.jobsearch.repository

import com.jobsearch.entity.Vacancy
import org.springframework.data.jpa.repository.JpaRepository

interface VacancyRepository: JpaRepository<Vacancy, Int> {
}