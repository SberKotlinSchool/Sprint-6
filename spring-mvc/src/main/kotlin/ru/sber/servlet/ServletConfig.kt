package ru.sber.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.sber.database.DbService
import ru.sber.servlet.LoginServlet
import ru.sber.servlet.filter.AuthFilter
import ru.sber.servlet.filter.LogFilter

@Configuration
class ServletConfig {

    @Bean
    fun loginServletBean(dbService: DbService): ServletRegistrationBean<*>? {//Создаст и зарегистрирует сервлет
        val bean: ServletRegistrationBean<*> = ServletRegistrationBean(
                LoginServlet(dbService), "/login"
        )
        bean.setLoadOnStartup(1)
        return bean
    }

    /**
     * Фильтр проверки на аутентификацию
     */
    @Bean
    fun authFilter(): FilterRegistrationBean<*>? {
        val registrationBean = FilterRegistrationBean<AuthFilter>()
        registrationBean.filter = AuthFilter()
        registrationBean.addUrlPatterns("/app/*", "/api/*")
        registrationBean.order = 2
        return registrationBean
    }

    /**
     * Фильтр логирования входящих http-запросов
     */
    @Bean
    fun logFilter(): FilterRegistrationBean<*>? {
        val registrationBean = FilterRegistrationBean<LogFilter>()
        registrationBean.filter = LogFilter()
        registrationBean.addUrlPatterns("/app/*", "/api/*")
        registrationBean.order = 1
        return registrationBean
    }
}

