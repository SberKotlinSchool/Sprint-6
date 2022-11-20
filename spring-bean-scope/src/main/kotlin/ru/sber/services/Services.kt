package ru.sber.services

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service


@Component
@Scope("singleton")
class SingletonService

@Component
@Scope("prototype")
class PrototypeService
