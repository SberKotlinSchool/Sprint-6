package ru.sber.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import ru.sber.services.PrototypeService
import ru.sber.services.SingletonService

@Configuration
@ComponentScan("ru.sber.services")
class ServicesConfig {

    @Bean
    @Scope("singleton")
    fun singletonService(): SingletonService {
        return SingletonService()
    }

    @Bean
    @Scope("prototype")
    fun prototypeService(): PrototypeService {
        return PrototypeService()
    }
}