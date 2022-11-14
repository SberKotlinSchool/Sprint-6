package ru.sberbank.school.mvchomework.controller

import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletConfig
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@WebServlet(name = "AuthServlet", urlPatterns = ["/login"])
class AuthServlet : HttpServlet() {
    override fun init(config: ServletConfig?) {
        super.init(config)
        print("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
    }
//
//    override fun service(req: ServletRequest?, res: ServletResponse?) {
//        super.service(req, res)
//    }

    @Throws(ServletException::class, IOException::class)
    override fun doGet(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        response.contentType = "text/html;charset=UTF-8"
        val out = response.writer
        out.use { out ->
            out.println("<html>")
            out.println("<head>")
            out.println("<title>MyFirstServlet</title>")
            out.println("</head>")
            out.println("<body>")
            out.println("<h2>Servlet MyFirstServlet at " + request.contextPath + "</h2>")
            out.println("</body>")
            out.println("</html>")
            out.flush()
        }
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(
        request: HttpServletRequest?,
        response: HttpServletResponse?
    ) {
        //Do some other work
    }

    override fun destroy() {
        super.destroy()
    }
}