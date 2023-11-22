package ru.sber.services

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

@Service("singletonService")
class SingletonService {
    override fun toString(): String {
        return "I am singletonService"
    }
}

@Service("prototypeService")
@Scope("prototype")
class PrototypeService {
    override fun toString(): String {
        return "I am prototypeService"
    }
}