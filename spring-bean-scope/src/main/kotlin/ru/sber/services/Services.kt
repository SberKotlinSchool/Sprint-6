package ru.sber.services

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component("singletonService")
@Scope("singleton")
class SingletonService {
}



@Component("prototypeService")
@Scope("prototype")
class PrototypeService {
}