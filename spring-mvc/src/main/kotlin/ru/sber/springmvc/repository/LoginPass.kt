package ru.sber.springmvc.repository

//@Repository
//data class LoginPass (@Value("") val userName: String, @Value("") val password: String) {
//    var localDB = HashMap<String, String>()
//}

data class LoginPass (val userName: String, val password: String) {
    var localDB = HashMap<String, String>()
}