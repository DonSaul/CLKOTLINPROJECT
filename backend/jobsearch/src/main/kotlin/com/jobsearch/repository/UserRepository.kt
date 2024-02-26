package com.jobsearch.repository

import com.jobsearch.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int>{

}