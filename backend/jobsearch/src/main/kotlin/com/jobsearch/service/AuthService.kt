package com.jobsearch.service

import com.jobsearch.dto.UserDTO
import com.jobsearch.entity.User
import com.jobsearch.jwt.JwtProvider
import com.jobsearch.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    val userService: UserService,
) : UserDetailsService {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var jwtProvider: JwtProvider
    fun register(userDto: UserDTO) {

        userService.createUser(userDto)

    }


    fun findByUsername(username: String): User? {
        return userRepository.findByEmail(username).orElse(null)
    }


    fun authenticate(username: String, password: String): String {
        val userDetails = loadUserByUsername(username)
        if (passwordEncoder.matches(password, userDetails.password)) {
            return jwtProvider.generateJwtToken(userDetails)
        } else {
            throw BadCredentialsException("Invalid credentials.")
        }
    }


    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
            .orElseThrow { UsernameNotFoundException("User not found.") }
        return UserDetailsImpl.build(user)
//
//        val authorities = user.role?.name?.let { SimpleGrantedAuthority(it) }
//
//            .username(user.email)
//            .password(user.password)
//            .authorities(authorities)
//            .build()
    }
}
