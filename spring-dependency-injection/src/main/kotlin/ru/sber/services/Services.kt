package ru.sber.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
class ConstructorInjectionService(private val service: Service) {

    @Autowired
    constructor(service: Service, @Value("-1") someField: Int) : this(service)

    override fun toString(): String {
        return "$service was injected into ConstructorInjectionService"
    }
}

@Component
class FieldInjectionService {
    @Autowired
    private lateinit var service: Service

    override fun toString(): String {
        return "$service was injected into FieldInjectionService"
    }
}

@Component
class SetterInjectionService {
    @set:Autowired
    lateinit var service: Service

    override fun toString(): String {
        return "$service was injected into SetterInjectionService"
    }
}

@Component
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