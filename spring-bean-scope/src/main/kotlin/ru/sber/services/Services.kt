package ru.sber.services

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
class SingletonService

@Component
@Scope("prototype")
class PrototypeService