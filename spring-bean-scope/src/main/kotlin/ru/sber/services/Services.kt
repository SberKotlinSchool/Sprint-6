package ru.sber.services

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

class Services {
    @Component("singletonService")
    @Scope("singleton")
    class SingletonServices {
        override fun toString(): String {
            return "I am singletonServices"
        }
    }

    @Component("prototypeService")
    @Scope("prototype")
    class PrototypeService {
        override fun toString(): String {
            return "I am prototypeService"
        }
    }
}