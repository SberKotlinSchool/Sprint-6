package ru.sber.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import ru.sber.services.FourthService
import ru.sber.services.ThirdService

@Configuration
@ComponentScan("ru.sber.anotherservices")
class AnotherServicesConfig {


    @Bean
    fun fourthService(): FourthService {
        return FourthService()
    }
}