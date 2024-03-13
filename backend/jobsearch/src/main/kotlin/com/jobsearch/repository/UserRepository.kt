package com.jobsearch.repository

import com.jobsearch.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Int> {
    fun findByEmail(email: String): Optional<User>
    fun findByResetPasswordToken(token: String): Optional<User>

}
