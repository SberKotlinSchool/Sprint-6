package ru.sber.services

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

@Service("singletonService")
class SingletonService {
}

@Service("prototypeService")
@Scope("prototype")
class PrototypeService {
}