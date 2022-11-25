package ru.sber.springmvc.dto

//@Repository
//data class LoginPass (@Value("") val userName: String, @Value("") val password: String) {
//    var localDB = HashMap<String, String>()
//}

data class User (val userName: String,
                 val password: String)