package ru.sber.mvc.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class AclMethodSecurityConfig: GlobalMethodSecurityConfiguration() {

    @Autowired
    lateinit var defaultMethodSecurityExpressionHandler: MethodSecurityExpressionHandler


    override fun createExpressionHandler(): MethodSecurityExpressionHandler =
        defaultMethodSecurityExpressionHandler
}
