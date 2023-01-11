package ru.morningcake.addressbook.conrtoller

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import ru.morningcake.addressbook.exception.EntityNotFoundException
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
@RequestMapping("/error")
class ErrorController {

    @ExceptionHandler(EntityNotFoundException::class)
    @GetMapping("/404")
    fun handleError(model: Model, ex: EntityNotFoundException, req: HttpServletRequest): String? {
        setModel(model, ex, req, 404)
        return "errors"
    }

    private fun setModel(model: Model, th: Throwable, req: HttpServletRequest, status: Int) {
        model.addAttribute("status", status)
        model.addAttribute("message", th.message)
        model.addAttribute("url", req.requestURL.toString())
        model.addAttribute("method", req.method)
    }
}