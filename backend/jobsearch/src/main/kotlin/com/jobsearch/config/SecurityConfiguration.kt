package com.jobsearch.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
class SecurityConfig(private val userDetailsService: UserDetailsService) {

    @Autowired
    private lateinit var authService: AuthService
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests{ authRequests ->
                authRequests
                    .requestMatchers("/api/v1/users/**").permitAll()
                    .requestMatchers("/api/v1/cvs/**").permitAll()
                    .requestMatchers("/api/v1/skills/**").permitAll()
                    .requestMatchers("/api/v1/vacancy/**").permitAll()
                    .requestMatchers("/api/v1/job-family/**").permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin(Customizer.withDefaults())
            .build()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(UserDetailsService { username ->
            val user = authService.findByUsername(username)
            if (user) {
                User.withUsername(user.email)
                    .password(passwordEncoder().encode(user.password))
                    .roles(user.roles.first().name)
                    .build()
            } else {
                throw UsernameNotFoundException("User not found.")
            }
        }).passwordEncoder(passwordEncoder())
    }
}
