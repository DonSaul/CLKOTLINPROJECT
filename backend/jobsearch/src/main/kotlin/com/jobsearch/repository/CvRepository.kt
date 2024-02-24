package com.jobsearch.repository

import com.jobsearch.entity.Cv
import org.springframework.data.jpa.repository.JpaRepository

interface CvRepository: JpaRepository<Cv, Int>