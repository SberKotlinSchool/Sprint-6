package ru.sber.mvc.resource

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import ru.sber.mvc.entity.Client
import ru.sber.mvc.entity.Greeting
import ru.sber.mvc.service.Authentication


@Controller
class LoginResource {
    private val authentication: Authentication
    @Autowired
    constructor(authentication: Authentication) {
        this.authentication = authentication
    }


    @RequestMapping(value = ["/login"], method = [RequestMethod.GET])
    public fun getClientForm(model: Model): String {
        model.addAttribute("clientForm", Client())
        return "client"
    }

    @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
    public fun auth(@ModelAttribute client: Client, model: Model): String {
        model.addAttribute("clientForm", client)
        if (authentication.authentication(client)) {
            return "auth"
        }
        return "client"
    }
}


@Controller
class GreetingController {
    @RequestMapping(value = ["/greeting"], method = [RequestMethod.GET])
    fun greetingForm(model: Model): String {
        model.addAttribute("greeting", Greeting())
        return "greeting"
    }

    @RequestMapping(value = ["/greeting"], method = [RequestMethod.POST])
    fun greetingSubmit(@ModelAttribute greeting: Greeting?, model: Model): String {
        model.addAttribute("greeting", greeting)
        System.out.println(greeting!!.getId());
        return "result"
    }
}