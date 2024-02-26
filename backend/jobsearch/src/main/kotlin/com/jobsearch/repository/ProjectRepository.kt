package com.jobsearch.repository

import com.jobsearch.entity.Project
import org.springframework.data.jpa.repository.JpaRepository

interface ProjectRepository: JpaRepository<Project,Int> {
}