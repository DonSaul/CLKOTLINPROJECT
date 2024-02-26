package com.jobsearch

import com.jobsearch.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User.*
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(securedEnabled: Boolean = true, jsr250Enabled: Boolean = true) {

    @Autowired
    private lateinit var authService: AuthService


    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Bean
    @Throws(Exception::class)
    fun configure(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests() { authRequest ->
                authRequest
                    .requestMatchers("/api/v1/auth/register").permitAll()
                    .anyRequest().authenticated()
            }
            .build()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder)
        auth.userDetailsService(UserDetailsService { username ->
            val user = authService.findByUsername(username)
            if (user != null) {
                withUsername(user.email).password(passwordEncoder.encode(user.password))
                    .roles(*user.roles.map { it.name }.toTypedArray()).build()
            } else {
                throw UsernameNotFoundException("User not found.")
            }
        })
    }
}