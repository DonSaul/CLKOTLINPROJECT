package com.jobsearch.jwt
import com.jobsearch.exception.NotFoundException
import com.jobsearch.repository.UserRepository
import com.jobsearch.service.UserDetailsImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class JwtProvider (
        @Value("\${jwt.secret}")
        private val jwtSecret: String? = null,

        @Value("\${jwt.expiration}")
        private val jwtExpiration: Int? = null,

        private val userRepository:UserRepository)

{


    fun generateJwtToken(userDetails: UserDetails): String {
        val claims = Jwts.claims().setSubject(userDetails.username)
        claims["roles"] = userDetails.authorities
        val user = userRepository.findByEmail(userDetails.username).orElse(null)
        claims["first_name"] = user.firstName
        claims["last_name"] = user.lastName
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtExpiration!! * 1000))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun validateJwtToken(token: String?): Boolean {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
        return true
    }

    fun getUserNameFromJwtToken(token: String?): String {
        return Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body
            .subject
    }
}


@Component
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider,
    private val userRepository: UserRepository,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = extractJwtFromRequest(request)

        if (jwt != null && jwtProvider.validateJwtToken(jwt)) {
            val username = jwtProvider.getUserNameFromJwtToken(jwt)
            val userEntity = userRepository.findByEmail(username)
            if (userEntity.isEmpty) throw NotFoundException("User with email $username not found")
            val userDetails = UserDetailsImpl.build(userEntity.get())
            val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun extractJwtFromRequest(request: HttpServletRequest): String? {
        val authorizationHeader = request.getHeader("Authorization")
        return if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader.substring(7)
        } else null
    }
}

