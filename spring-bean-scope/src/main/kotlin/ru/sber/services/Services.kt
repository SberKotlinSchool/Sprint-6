package ru.sber.services

import org.springframework.stereotype.Service
import org.springframework.context.annotation.Scope

@Service("singletonService")
@Scope("singleton")
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