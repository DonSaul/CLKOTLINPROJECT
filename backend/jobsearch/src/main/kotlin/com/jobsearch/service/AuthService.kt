package com.jobsearch.service

import com.jobsearch.dto.UserDTO
import com.jobsearch.entity.Role
import com.jobsearch.entity.User
import com.jobsearch.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    fun register(userDto: UserDTO) {
        val user = User(
            username = userDto.username,
            password = passwordEncoder.encode(userDto.password),
            roles = setOf(Role(name = "ROLE_USER")) // asumimos que todos los usuarios nuevos son usuarios normales
        )
        userRepository.save(user)
    }

    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username).orElse(null)
    }

    fun getEncoder(): PasswordEncoder {
        return passwordEncoder
    }
}
