package ru.sber.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import ru.sber.services.CombinedBean
import ru.sber.services.processors.MyBeanPostProcessor

@Configuration
@ComponentScan("ru.sber.services")
class ServicesConfig {
    @Bean
    fun customBeanPostProcessor(): MyBeanPostProcessor {
        return MyBeanPostProcessor()
    }
    @Bean
    fun combinedBean() = CombinedBean()
}