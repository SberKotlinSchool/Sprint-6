package ru.sber.services

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

@Service("singletonService")
class FirstService {
    override fun toString(): String {
        return "I am firstService"
    }
}

@Service("prototypeService")
@Scope("prototype")
class SecondService {
    override fun toString(): String {
        return "I am secondService"
    }
}
