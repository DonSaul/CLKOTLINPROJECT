package com.jobsearch.service

import com.jobsearch.dto.UserDTO
import com.jobsearch.entity.Role
import com.jobsearch.entity.User
import com.jobsearch.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    @Autowired private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    fun register(userDto: UserDTO) {
        val user = User(
            firstName = userDto.firstName,
            lastName = userDto.lastName,
            email = userDto.email,
            password = passwordEncoder.encode(userDto.password),
            role = Role(name = "ROLE_USER")
        )
        userRepository.save(user)
    }

    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username).orElse(null)
    }


    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("User not found.") }

        val authorities = user.role?.name?.let { SimpleGrantedAuthority(it) }

        return org.springframework.security.core.userdetails.User
            .withUsername(user.email)
            .password(user.password)
            .authorities(authorities)
            .build()
    }
}
