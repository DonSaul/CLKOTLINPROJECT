package com.jobsearch

import com.jobsearch.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.DefaultSecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    @Autowired
    private lateinit var authService: AuthService

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
            .authorizeRequests()
            .requestMatchers("/api/v1/auth/register").permitAll()
            .anyRequest().authenticated()
            .and()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(UserDetailsService { username ->
            val user = authService.findByUsername(username)
            if (user != null) {
                org.springframework.security.core.userdetails.User.withUsername(user.username).password(authService.getEncoder().encode(user.password)).roles(*user.roles.map { it.name }.toTypedArray()).build()
            } else {
                throw UsernameNotFoundException("User not found.")
            }
        })
    }
}




