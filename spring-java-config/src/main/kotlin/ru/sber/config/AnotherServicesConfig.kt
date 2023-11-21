package ru.sber.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
@ComponentScan("ru.sber.anotherservices")
class AnotherServicesConfig {

    @Primary
    @Bean
    fun fourthService(): Any {
        return object {
            override fun toString(): String {
                return "I am fourthService"
            }
        }
    }

}