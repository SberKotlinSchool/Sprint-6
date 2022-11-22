package ru.sber.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import ru.sber.services.FourthService

@Configuration
@ComponentScan("ru.sber.services")
class AnotherServicesConfig {
    @Bean
    fun fourthService() : FourthService {
        return FourthService()
    }
}