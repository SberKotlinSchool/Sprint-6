package ru.sber.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import ru.sber.services.*

@Configuration
@ComponentScan("ru.sber.services")
class ServicesConfig {
    @Bean
    fun firstService(): SingletonService {
        return SingletonService()
    }

    @Bean
    @Scope(value = "prototype")
    fun prototypeService(): PrototypeService {
        return PrototypeService()
    }
}