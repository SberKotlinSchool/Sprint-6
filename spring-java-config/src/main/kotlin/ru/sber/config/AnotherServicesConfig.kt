package ru.sber.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("ru.sber.anotherservices")
class AnotherServicesConfig {


    @Bean
    fun fourthService(): FourthService {
        return FourthService()
    }
}