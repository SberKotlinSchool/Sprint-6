package ru.sber.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import ru.sber.services.FirstServiceImpl
import ru.sber.services.SecondServiceImpl
import ru.sber.services.ServiceInterface

@Configuration
@ComponentScan("ru.sber.services")
class ServicesConfig {
    @Bean
    @Primary
    fun services(): ArrayList<ServiceInterface> {
        return arrayListOf(FirstServiceImpl(), SecondServiceImpl())
    }
}