package com.example.springmvcsber.configuration

import com.example.springmvcsber.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.DelegatingPasswordEncoder


@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http {
            formLogin {
                loginPage = "/login.html"
                defaultSuccessUrl("/app", true)
                permitAll()
            }
            authorizeRequests {
                authorize("/rest/**", "hasRole('ADMIN') or hasRole('API')")
                authorize("/app/**", "hasRole('ADMIN') or hasRole('API')")
                authorize("/**", authenticated)
            }

            csrf { ignoringAntMatchers("/rest/**", "/login") }

        }
    }

    @Bean
    fun delegatingPasswordEncoder(): DelegatingPasswordEncoder = DelegatingPasswordEncoder(
        "bcrypt", mapOf("bcrypt" to BCryptPasswordEncoder(14))
    )

    @Bean
    fun authProvider(userService: UserService): AuthenticationProvider {
        return DaoAuthenticationProvider().apply {
            setUserDetailsService(userService)
            setPasswordEncoder(delegatingPasswordEncoder())
        }
    }
}

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class GlobalMethodSecurityConfig : GlobalMethodSecurityConfiguration()