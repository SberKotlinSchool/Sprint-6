package ru.sber.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import ru.sber.services.PrototypeService
import ru.sber.services.SingletonService

@Configuration
@ComponentScan("ru.sber.services")
class ServicesConfig {
    @Bean
    fun getSingletonService(): SingletonService {
        return SingletonService()
    }

    @Bean
    fun getPrototoypeService(): PrototypeService {
        return PrototypeService()
    }

}