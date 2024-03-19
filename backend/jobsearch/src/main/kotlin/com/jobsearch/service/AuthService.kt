package com.jobsearch.service

import com.jobsearch.dto.UserRequestDTO
import com.jobsearch.entity.User
import com.jobsearch.exception.NotFoundException
import com.jobsearch.exception.UserAlreadyExistsException
import com.jobsearch.jwt.JwtProvider
import com.jobsearch.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
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
    fun register(userRequestDto: UserRequestDTO) {
        if (userRepository.findByEmail(userRequestDto.email).isPresent) throw UserAlreadyExistsException("User with email ${userRequestDto.email} already on the database")
        userService.createUser(userRequestDto)
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
            .orElseThrow { NotFoundException("User with email $username not found.") }
        return UserDetailsImpl.build(user)
    }
}
