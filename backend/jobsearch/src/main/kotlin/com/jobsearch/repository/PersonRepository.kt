package com.jobsearch.repository

import com.jobsearch.entity.Person
import org.springframework.data.jpa.repository.JpaRepository

interface PersonRepository: JpaRepository<Person, Int> {
}