package ru.sber.agadressbook.config

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@ComponentScan("ru.sber.agadressbook")
@ServletComponentScan("ru.sber.agadressbook.servlets", "ru.sber.agadressbook.filters")
@EnableWebSecurity
class AddressBookConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.authorizeHttpRequests()
            .antMatchers("/addressbook/**").authenticated()
            .and()
            .formLogin()
            .defaultSuccessUrl("/addressbook/list",true)
            .and()
            .logout().logoutSuccessUrl("/login")
    }
}