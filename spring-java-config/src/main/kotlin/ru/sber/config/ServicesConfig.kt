package ru.sber.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.sber.services.FirstService
import ru.sber.services.FourthService
import ru.sber.services.SecondService
import ru.sber.services.ThirdService

@Configuration
class ServicesConfig {
    @Bean("firstService")
    fun service(): FirstService {
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

    @Bean
    fun fourthService(): FourthService {
        return FourthService()
    }
}