package ru.sber.springmvc.servlet

import org.springframework.beans.factory.annotation.Autowired
import ru.sber.springmvc.model.User
import ru.sber.springmvc.service.UserService
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(
    urlPatterns = ["/user/find"]
)
class FindUserServlet: HttpServlet() {

    lateinit var userService: UserService
        @Autowired set

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val searches = request.reader.readLine()
            .split("&")
            .map { it.split("=") }
            .filter { it[1].isNotEmpty() }
            .toList()

        if (searches.isEmpty()) {
            response.sendRedirect("/user/search")
        } else {
            val searchCriteriaList = searches.map {
                when (it[0]) {
                    "name" -> { user: User -> user.name?.contains(it[1]) }
                    "login" -> { user: User -> user.login?.contains(it[1]) }
                    "address" -> { user: User -> user.address?.addr?.contains(it[1]) }
                    else -> { _ -> true }
                }
            }.toList()
            val users = userService.getUserList()
                .filter { user -> searchCriteriaList.all { criteriaFunc -> criteriaFunc.invoke(user) ?: false } }

            val resp = StringBuilder("""
                <html>
                <style type="text/css">table {border: 1px black solid;} td {border: 1px black solid;} th {border: 1px black solid;}</style>
                <head>
                <meta charset="utf-8">
                <title>Результаты поиска</title>
                </head>
                <body>
                <h3>Результаты поиска</h3>
                <br/>
            """.trimIndent())

            if (users.isEmpty()) {
                resp.append("Не найдено")
            } else {
                resp.append("""
                    <table style="width: 80%">
                    <thead>
                    <tr>
                    <td style="width: 150px">Id</td>
                    <td style="width: 300px">Имя</td>
                    <td style="width: 100px">Логин</td>
                    <td style="width: 500px">Адрес</td>
                    </tr>
                    </thead>
                    <tbody>
                """.trimIndent())

                users.onEach { user ->
                    resp.append("""
                        <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.login}</td>
                        <td>${user.address?.addr ?: ""}</td>
                        </tr>
                    """.trimIndent())
                }
                resp.append("""
                    </tbody>
                    </table>
                """.trimIndent())
            }
            resp.append("""
                <br/>
                <a href="/">На главную</a>
                </body>
                </html>
            """.trimIndent())

            response.contentType = "text/html;charset=utf-8"
            response.writer.print(resp.toString())
        }
    }
}
