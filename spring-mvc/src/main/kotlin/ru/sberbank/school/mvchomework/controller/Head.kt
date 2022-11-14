package ru.sberbank.school.mvchomework.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class Head @Autowired constructor(
    @Qualifier("gtt") var sss: String
) {
    fun run() {
        print(sss)
    }
}