package ru.sber.services

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

class FirstService {
    override fun toString(): String {
        return "I am firstService"
    }
}

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

@Service
class FourthService {
    override fun toString(): String {
        return "I am fourthService"
    }
}