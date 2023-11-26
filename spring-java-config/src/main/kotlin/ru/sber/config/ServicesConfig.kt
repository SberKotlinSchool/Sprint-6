package ru.sber.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import ru.sber.services.FirstService

@Configuration
@ComponentScan
class ServicesConfig {
    @Bean
    fun firstService(): FirstService {
        return FirstService()
    }

    @Bean
    fun secondService(): SecondService {
        return SecondService()
    }

    @Bean
    fun thirdService(): ThirdService {
        return ThirdService()
    }
}