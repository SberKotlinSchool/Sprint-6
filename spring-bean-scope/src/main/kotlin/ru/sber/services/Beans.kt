package ru.sber.services

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

@Service
@Scope("singleton")
class SingletonService {

}

@Service
@Scope("prototype")
class PrototypeService {

}
