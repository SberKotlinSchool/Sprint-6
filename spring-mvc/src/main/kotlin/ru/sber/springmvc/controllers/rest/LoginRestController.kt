package ru.sber.springmvc.controllers.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.sber.springmvc.dto.User
import ru.sber.springmvc.services.AuthService

@RestController
@RequestMapping("/api/login")
class LoginRestController @Autowired constructor (private val service: AuthService) {

    @GetMapping
    fun login (@RequestParam(value = "name", defaultValue = "") userName: String,
              @RequestParam(value = "pass", defaultValue = "") password: String): Map<String, String> {
        val map = HashMap<String, String>()
        var ans = "success"
        if (!service.checkUserPass(User(userName, password))) {
            ans = "fail"
        }
        map["status"] = ans
        return map
    }
}