package ru.sber.services

import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class PrototypeService {
}