package ru.sber.springmvc.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class AclMethodSecurityConfiguration {
    @Autowired
    var defaultMethodSecurityExpressionHandler: MethodSecurityExpressionHandler? = null

    protected fun createExpressionHandler(): MethodSecurityExpressionHandler? {
        return defaultMethodSecurityExpressionHandler
    }
}