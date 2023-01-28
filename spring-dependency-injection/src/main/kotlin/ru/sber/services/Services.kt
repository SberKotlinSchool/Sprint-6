package ru.sber.services

import org.springframework.stereotype.Component

@Component
class ConstructorInjectionService(private val service: Service) {

    constructor(service: Service, someField: Int) : this(AnotherService())

    override fun toString(): String {
        return "$service was injected into ConstructorInjectionService"
    }
}

@Component
class FieldInjectionService {
    private lateinit var service: Service

    override fun toString(): String {
        return "$service was injected into FieldInjectionService"
    }
}

@Component
class SetterInjectionService {
    lateinit var service: Service

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