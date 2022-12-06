package ru.sber.services

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

@Service(value = "singletonService")
@Scope("singleton")
class SingleTonService() {
//..some code here
}

@Service(value = "prototypeService", )
@Scope("prototype")
class PrototypeService {
    //..some code here
}
