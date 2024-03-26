package com.jobsearch.config

import com.jobsearch.jwt.JwtAuthenticationFilter
import com.jobsearch.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.token.TokenService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
class SecurityConfig(private val userDetailsService: UserDetailsService) {

    @Autowired
    private lateinit var authService: AuthService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var jwtAuthenticationFilter: JwtAuthenticationFilter

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { authRequests ->
                authRequests
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers("/api/v1/users/**").authenticated()
                    .requestMatchers("/api/v1/application/**").authenticated()
                    .requestMatchers("/api/v1/cvs/**").authenticated()
                    .requestMatchers("/api/v1/skills/**").permitAll()
                    // Vacancy endpoints
                    .requestMatchers(HttpMethod.GET, "/api/v1/vacancy").authenticated()
                    .requestMatchers(HttpMethod.GET, "/api/v1/vacancy/**").authenticated()
                    .requestMatchers(HttpMethod.GET, "/api/v1/vacancy/search").authenticated()
                    .requestMatchers(HttpMethod.GET, "/api/v1/vacancy/my-vacancies").hasAuthority("manager")
                    .requestMatchers(HttpMethod.POST, "/api/v1/vacancy").hasAuthority("manager")
                    .requestMatchers(HttpMethod.GET, "/api/v1/candidates/search").hasAuthority("manager")
                    .requestMatchers(HttpMethod.GET, "/api/v1/candidates/vacancy/**").hasAuthority("manager")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/vacancy/**").hasAuthority("manager")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/vacancy/**").hasAuthority("manager")
//                    .requestMatchers(HttpMethod.GET, "/api/v1/vacancy/manage").hasAuthority("manager")
                    .requestMatchers("/api/v1/job-family/**").permitAll()
                    .requestMatchers("/api/v1/application-status/**").permitAll()
                    .requestMatchers("/api/v1/invitations/**").permitAll()
                    .requestMatchers("/api/v1/notifications/**").permitAll()
                    .requestMatchers("/api/v1/recoverPassword/**").permitAll()
                    .requestMatchers("api/v1/conversation/**").permitAll()

                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(UserDetailsService { username ->
            val user = authService.findByUsername(username)
            if (user != null) {
                User.withUsername(user.email)
                    .password(user.password)
                    .roles(user.role?.name)
                    .build()
            } else {
                throw UsernameNotFoundException("User not found.")
            }
        })
    }
}