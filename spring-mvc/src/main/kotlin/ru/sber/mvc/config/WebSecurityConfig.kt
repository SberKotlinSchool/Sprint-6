package ru.sber.mvc.config

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/api/**", hasAnyRole("API", "ADMIN"))
                authorize("/app/**", authenticated)
                authorize("/login", permitAll)
                authorize(anyRequest, denyAll)
            }
            csrf { disable() }
            headers {
                frameOptions {
                    //разрешаем работу в iframe внутри домена для h2-console
                    sameOrigin = true
                }
            }
            formLogin {
                loginPage = "/login"
            }
        }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        DelegatingPasswordEncoder("bcrypt", mapOf("bcrypt" to BCryptPasswordEncoder()))

    @Bean
    fun authProvider(userDetailService: UserDetailsService, passwordEncoder: PasswordEncoder): AuthenticationProvider =
        DaoAuthenticationProvider().apply {
            setUserDetailsService(userDetailService)
            setPasswordEncoder(passwordEncoder)
        }
}
