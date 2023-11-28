package ru.sber

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableWebMvc
class MvcConfig : WebMvcConfigurer {

  override fun addViewControllers(registry: ViewControllerRegistry) {
    registry.addViewController("/").setViewName("index")
  }
}