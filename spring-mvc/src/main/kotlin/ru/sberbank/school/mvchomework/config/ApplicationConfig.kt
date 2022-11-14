package ru.sberbank.school.mvchomework.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import ru.sberbank.school.mvchomework.controller.AuthServlet
import ru.sberbank.school.mvchomework.controller.Head


@Configuration
@ComponentScan("ru.sberbank.school.mvchomework.controller")
class ApplicationConfig {

    @Bean
    fun authServlet(): AuthServlet {
        print("+++++++++++++++++   AuthServlet   ))))))))))))))")
      return AuthServlet()
    }

    @Bean
    fun gtt(): String {
        print("+++++++++++++++++   String   ))))))))))))))")
        return "AuthServlet()"
    }

    @Bean
    fun asd() = Head(gtt())
}