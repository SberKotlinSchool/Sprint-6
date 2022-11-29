package ru.sber.agadressbook.config

import org.h2.store.Data
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.provisioning.JdbcUserDetailsManager
import javax.sql.DataSource


@Configuration
@ComponentScan("ru.sber.agadressbook")
@ServletComponentScan("ru.sber.agadressbook.servlets", "ru.sber.agadressbook.filters")
@EnableWebSecurity
class AddressBookConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var datasource: DataSource

    override fun configure(http: HttpSecurity) {
        http.authorizeHttpRequests()
            .antMatchers("/addressbook/*").authenticated()
            .antMatchers("/addressbook/api/**").hasRole("API")
            .and()
            .formLogin()
            .defaultSuccessUrl("/addressbook/list",true)
            .and()
            .logout().logoutSuccessUrl("/login")
    }


    @Bean
    fun user (dataSource: DataSource) : JdbcUserDetailsManager {

        val user : UserDetails = User.builder()
            .username("user")
            .password("{bcrypt}\$2a\$12\$hgdyBekz/qbZ0ifbgPdrO.jjJwG7AwBhnmW2RLiHyDI/suPJBipWy")
            .roles("USER")
            .build()

        val admin : UserDetails = User.builder()
            .username("admin")
            .password("{bcrypt}\$2a\$12\$hgdyBekz/qbZ0ifbgPdrO.jjJwG7AwBhnmW2RLiHyDI/suPJBipWy")
            .roles("USER","API")
            .build()

        val superAdmin : UserDetails = User.builder()
            .username("superadmin")
            .password("{bcrypt}\$2a\$12\$hgdyBekz/qbZ0ifbgPdrO.jjJwG7AwBhnmW2RLiHyDI/suPJBipWy")
            .roles("USER","API","ADMIN")
            .build()

        val jdbcUserDetailsManager = JdbcUserDetailsManager(dataSource)

        if (jdbcUserDetailsManager.userExists(user.username)) {
            jdbcUserDetailsManager.deleteUser(user.username)
        }

        if (jdbcUserDetailsManager.userExists(admin.username)) {
            jdbcUserDetailsManager.deleteUser(admin.username)
        }

        if (jdbcUserDetailsManager.userExists(superAdmin.username)) {
            jdbcUserDetailsManager.deleteUser(superAdmin.username)
        }

        jdbcUserDetailsManager.createUser(user)
        jdbcUserDetailsManager.createUser(admin)
        jdbcUserDetailsManager.createUser(superAdmin)

        return jdbcUserDetailsManager;

    }
}