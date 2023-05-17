package com.example.springmvc.servlet

import com.example.springmvc.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


//минимум один сервлет (функционал может быть любой, но предлагаю реализовать форму аутентификации)

//Необходимо реализовать минимальное подобие аутентификации без применения spring security,
// что бы к основным ресурсам приложения был доступ только после ввода заранее определенной пары логин-пароль.

//Для этого можно воспользоваться следующим алгоритмом:
//получаем логин и пароль из формы
//если они соответствуют сохраненной паре логин-пароль, добавляем Cookie с именем auth и временем входа

//Далее при обращении к защищенным ресурсам, производим проверку в фильтре наличия Cookie с именем auth и проверяем,
// что время в этом значении меньше текущего.
//Eсли Cookie отсутствует или имеет неверное значение - делаем redirect на форму входа.



@WebServlet(urlPatterns = ["/login"])
class AuthServlet(@Autowired val userRepository: UserRepository) : HttpServlet() {
    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        req!!.getRequestDispatcher("/login.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        val username = req!!.getParameter("username")
        val password = req.getParameter("password")


        if (userRepository.checkUser(username, password)) {
            val cookie = Cookie("auth", LocalDateTime.now().toString())
            cookie.maxAge = 24 * 60 * 60
            resp!!.addCookie(cookie)
            resp.sendRedirect("app/list")
        }
        else {
            resp!!.sendError(401, "Authorisation fail")
        }
    }
}