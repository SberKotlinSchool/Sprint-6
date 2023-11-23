package ru.sber.services

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
class SingletonService

@Scope("prototype")
@Configuration
class PrototypeService