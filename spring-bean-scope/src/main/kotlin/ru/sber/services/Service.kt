package ru.sber.services

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

@Scope("singleton")
@Service("singletonService")
class SingletonService

@Scope("prototype")
@Service("prototypeService")
class PrototypeService

