package ru.sber.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

interface PrimaryServiceInterface

@Component
@Qualifier("firstPrimaryService")
class FirstPrimaryServiceImpl : PrimaryServiceInterface {
    override fun toString(): String {
        return "FirstPrimaryServiceImpl"
    }
}

@Component
@Qualifier("secondPrimaryService")
class SecondPrimaryServiceImpl : PrimaryServiceInterface {
    override fun toString(): String {
        return "SecondPrimaryServiceImpl"
    }
}

@Component
class PrimaryBeanInjectionService {
    @Autowired
    @Qualifier("secondPrimaryService")
    private lateinit var primaryService: PrimaryServiceInterface

    override fun toString(): String {
        return "PrimaryBeanInjectionService(primaryService=$primaryService)"
    }
}