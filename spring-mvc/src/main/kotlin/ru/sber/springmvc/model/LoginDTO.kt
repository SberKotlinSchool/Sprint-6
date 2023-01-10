package ru.sber.springmvc.model

import java.io.Serializable

class LoginDTO: Serializable {
    var username: String = ""
    var password: String = ""
}