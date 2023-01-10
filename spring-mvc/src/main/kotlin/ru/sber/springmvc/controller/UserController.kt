package ru.sber.springmvc.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import ru.sber.springmvc.model.Address
import ru.sber.springmvc.model.FindUserDTO
import ru.sber.springmvc.model.LoginDTO
import ru.sber.springmvc.model.User
import ru.sber.springmvc.service.UserService
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Controller
class UserController @Autowired constructor(private val userService: UserService) {

    @GetMapping("/")
    fun userListView(model: Model): String {
        val users = userService.getUserList()
        model.addAttribute("users", users)
        return "users"
    }

    @GetMapping("/user/search")
    fun userFindView(model: Model): String {
        val findUserDTO = FindUserDTO()
        model.addAttribute("findUser", findUserDTO)
        return "find-user"
    }

    @GetMapping("/user/create")
    fun userCreateView(model: Model): String {
        val user = User()
        user.address = Address("", user)
        model.addAttribute("user", user)
        return "user"
    }

    @GetMapping("/user/edit/{id}")
    fun userEditView(@PathVariable id: Long, model: Model): String {
        return userService.findById(id)
            .map {
                model.addAttribute("user", it)
                "user"
            }
            .orElse("users")
    }

    @PostMapping("/user/save")
    fun userSave(@ModelAttribute/*("user")*/ user: User/*, result: BindingResult, model: Model*/): String {
        userService.saveUser(user)
        return "redirect:/"
    }

    @GetMapping("/login")
    fun loginForm(model: Model) : String {
        val loginDTO = LoginDTO()
        model.addAttribute("loginDTO", loginDTO)
        return "login"
    }

    @PostMapping("/login")
    fun login(@ModelAttribute loginDTO: LoginDTO, response: HttpServletResponse): String =
        userService.findByLoginAndPassword(loginDTO.username, loginDTO.password)
            .map {
                val cookie = Cookie("auth", "${Date().time}")
                cookie.isHttpOnly = true
                cookie.path = "/"
                response.addCookie(cookie)
                response.status = HttpStatus.OK.value()
                response.sendRedirect("/")
                "users"
            }.orElseGet {
                response.status = HttpStatus.UNAUTHORIZED.value()
                "login"
            }
}
