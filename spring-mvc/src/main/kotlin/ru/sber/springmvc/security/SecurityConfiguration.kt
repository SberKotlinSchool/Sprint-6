package ru.sber.springmvc.security

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfiguration : WebSecurityConfigurerAdapter() {
    @Autowired
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser("admin").password(passwordEncoder().encode("password")).roles("ADMIN")
            .and()
            .withUser("user").password(passwordEncoder().encode("password")).roles("USER")
    }

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            .and()
            .formLogin()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return object : PasswordEncoder {
            override fun encode(charSequence: CharSequence): String {
                return charSequence.toString()
            }

            override fun matches(charSequence: CharSequence, s: String): Boolean {
                return charSequence.toString() == s
            }
        }
    }
}