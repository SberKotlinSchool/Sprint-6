package ru.sber.services

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
class singletonService {
}

@Component
@Scope("prototype")
class prototypeService {
}
