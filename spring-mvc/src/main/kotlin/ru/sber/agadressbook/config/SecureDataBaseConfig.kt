package ru.sber.agadressbook.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import javax.sql.DataSource

@Configuration
@EnableMethodSecurity(securedEnabled = true)
//удалять может только пользователь superadmin
//пользователь user имеет доступ только на просмотр
//пользователь admin, имеет доступ к /adrressbook/api/*
//для всех пользователей пароль 123
class DatabaseConfig {

    @Value("\${spring.datasource.url}")
    private lateinit var datasourceUrl: String

    @Value("\${spring.datasource.driver-class-name}")
    private lateinit var dbDriverClassName: String

    @Value("\${spring.datasource.username}")
    private lateinit var dbUsername: String

    @Value("\${spring.datasource.password}")
    private lateinit var dbPassword: String

    @Bean
    fun dataSource(): DataSource =
        DriverManagerDataSource().apply {
            setDriverClassName(dbDriverClassName)
            url = datasourceUrl
            username = dbUsername
            password = dbPassword
        }
}