package ru.sber.springmvc.filter

import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment

@Configuration
@PropertySource("classpath:application.yaml")
class Config(private val env: Environment) {

    @Bean
    fun apiAuthFilter(): FilterRegistrationBean<ApiFilter> {
        val login = env.getProperty("auth.login")
        val password = env.getProperty("auth.password")

        val registrationBean = FilterRegistrationBean<ApiFilter>()
        registrationBean.filter = ApiFilter(Base64.encodeBase64String("$login:$password".toByteArray()))
        registrationBean.addUrlPatterns("/api/*")
        registrationBean.order = 1

        return registrationBean
    }

    @Bean
    fun mvcAuthFilter(): FilterRegistrationBean<MvcFilter> {
        val registrationBean = FilterRegistrationBean<MvcFilter>()
        registrationBean.filter = MvcFilter()
        registrationBean.addUrlPatterns("/app/*")
        registrationBean.order = 1

        return registrationBean
    }
}