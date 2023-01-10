package ru.sber.springmvc.model

import java.io.Serializable

class FindUserDTO: Serializable {
    var name = ""
    var login = ""
    var address = ""
}