package com.example.demospringsecurity.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeRequests { authorize ->
                authorize
                    .antMatchers("/admin").hasRole("ADMIN")
                    .antMatchers("/user").hasRole("USER")
                    .anyRequest().authenticated()
            }
            .httpBasic()

        return http.build()
    }

    @Bean
    fun users(): UserDetailsService {
        val users = User.withDefaultPasswordEncoder()
        val user = users
            .username("user")
            .password("user")
            .roles("USER")
            .build()
        val admin = users
            .username("admin")
            .password("admin")
            .roles("USER", "ADMIN")
            .build()

        return InMemoryUserDetailsManager(user, admin)
    }
}