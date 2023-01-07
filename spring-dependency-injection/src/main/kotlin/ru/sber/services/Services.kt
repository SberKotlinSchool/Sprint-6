package ru.sber.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
class ConstructorInjectionService @Autowired constructor(@Qualifier("service") private val service: Service) {
    constructor(service: Service, someField: Int) : this(service)

    override fun toString(): String {
        return "$service was injected into ConstructorInjectionService"
    }
}

@Component
class FieldInjectionService {
    @Autowired
    @Qualifier("service")
    private lateinit var service: Service

    override fun toString(): String {
        return "$service was injected into FieldInjectionService"
    }
}

@Component
class SetterInjectionService {
    lateinit var service: Service
        @Autowired @Qualifier("service") set

    override fun toString(): String {
        return "$service was injected into SetterInjectionService"
    }
}

@Component
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