package ru.sber.springmvc.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring5.view.ThymeleafViewResolver
import org.thymeleaf.templatemode.TemplateMode


@Configuration
@ComponentScan("ru.sber.springmvc")
@EnableWebMvc
class WebConfig @Autowired constructor(private val applicationContext: ApplicationContext) : WebMvcConfigurer {

    @Bean
    fun templateResolver(): SpringResourceTemplateResolver = SpringResourceTemplateResolver().also {
        it.setApplicationContext(applicationContext)
        it.prefix = "/WEB-INF/templates/"
        it.suffix = ".html"
        it.templateMode = TemplateMode.HTML
        it.isCacheable = false
        it.characterEncoding = "UTF-8"
    }

    @Bean
    fun templateEngine(): SpringTemplateEngine = SpringTemplateEngine().also {
        it.setTemplateResolver(templateResolver())
    }

    @Bean
    fun viewResolver(): ThymeleafViewResolver = ThymeleafViewResolver().also {
        it.templateEngine = templateEngine()
        it.order = 1
        it.characterEncoding = "UTF-8"
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("static/**").addResourceLocations("/WEB-INF/static/")
    }
}