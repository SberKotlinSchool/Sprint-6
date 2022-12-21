package ru.sber.services

import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service("firstService")
class FirstService {
    override fun toString(): String {
        return "I am firstService"
    }
}

@Service("secondService")
class SecondService {
    override fun toString(): String {
        return "I am secondService"
    }
}

@Component
class ThirdService {
    override fun toString(): String {
        return "I am thirdService"
    }
}

@Component
class FourthService {
    override fun toString(): String {
        return "I am fourthService"
    }
}