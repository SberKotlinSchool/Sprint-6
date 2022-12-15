package ru.sber.services

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
class Services {

    @Bean
    @Scope("singleton")
    fun singletonService(){
    }

    @Bean
    @Scope("prototype")
    fun prototypeService(){
    }
}