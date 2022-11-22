package ru.sber.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Conditional
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component("constructorInjectionService")
@Qualifier("service")
class ConstructorInjectionService {
    private val service : Service

    @Autowired
    constructor(service: Service) {
        this.service = service
    }
    constructor(service: Service, someField: Int) : this(AnotherService())

    override fun toString(): String {
        return "$service was injected into ConstructorInjectionService"
    }
}


@Component("fieldInjectionService")
class FieldInjectionService {
    @Autowired
    private lateinit var service: Service

    override fun toString(): String {
        return "$service was injected into FieldInjectionService"
    }
}


@Component
class SetterInjectionService {
    lateinit var service: Service
     @Autowired set

    override fun toString(): String {
        return "$service was injected into SetterInjectionService"
    }
}


@Component("service")
@Primary
class Service {
    override fun toString(): String {
        return "Service"
    }
}


@Component
class AnotherService : Service() {
    override fun toString(): String {
        return "AnotherService"
    }
}