package ru.sber.mvc.entity

//data class Candidate(val login: String, val password: String) {
//
//
//}

class Client {
    private var login: String = ""
    private var password: String = ""

    fun getLogin(): String {
        return login
    }

    fun setLogin(login: String) {
        this.login = login
    }

    fun getPassword(): String {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }
}

class Greeting {
    private var id: Long = 0
    private var content: String? = null
    fun getId(): Long {
        return id
    }

    fun setId(id: Long) {
        this.id = id
    }

    fun getContent(): String? {
        return content
    }

    fun setContent(content: String?) {
        this.content = content
    }
}