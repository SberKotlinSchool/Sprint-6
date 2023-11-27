package ru.sber.services

import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component("singletonService")
class SingletonService

@Component("prototypeService")
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class PrototypeService