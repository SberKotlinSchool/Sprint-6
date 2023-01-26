package ru.sber.mvc.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.ehcache.EhCacheFactoryBean
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.acls.AclPermissionCacheOptimizer
import org.springframework.security.acls.AclPermissionEvaluator
import org.springframework.security.acls.domain.AclAuthorizationStrategy
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl
import org.springframework.security.acls.domain.ConsoleAuditLogger
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy
import org.springframework.security.acls.domain.EhCacheBasedAclCache
import org.springframework.security.acls.jdbc.BasicLookupStrategy
import org.springframework.security.acls.jdbc.JdbcMutableAclService
import org.springframework.security.acls.jdbc.LookupStrategy
import org.springframework.security.acls.model.PermissionGrantingStrategy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import javax.sql.DataSource

@Configuration
class AclConfig {

    @Autowired
    lateinit var dataSource: DataSource

    @Bean
    fun aclAuthorizationStrategy(): AclAuthorizationStrategy =
        AclAuthorizationStrategyImpl(SimpleGrantedAuthority("ROLE_ADMIN"))

    @Bean
    fun permissionGrantingStrategy(): PermissionGrantingStrategy =
        DefaultPermissionGrantingStrategy(ConsoleAuditLogger())

    @Bean
    fun aclCacheManager(): EhCacheManagerFactoryBean =
        EhCacheManagerFactoryBean()

    @Bean
    fun ehCacheFactoryBean(cacheManagerFactory: EhCacheManagerFactoryBean): EhCacheFactoryBean =
        EhCacheFactoryBean().apply {
            setCacheManager(cacheManagerFactory.getObject()!!)
            setCacheName("aclCache")
        }

    @Bean
    fun lookupStrategy(
        aclCache: EhCacheBasedAclCache,
        authorizationStrategy: AclAuthorizationStrategy
    ): LookupStrategy =
        BasicLookupStrategy(dataSource, aclCache, authorizationStrategy, ConsoleAuditLogger())

    @Bean
    fun aclCache(
        cacheFactory: EhCacheFactoryBean,
        permissionGrantingStrategy: PermissionGrantingStrategy,
        authorizationStrategy: AclAuthorizationStrategy
    ): EhCacheBasedAclCache =
        EhCacheBasedAclCache(cacheFactory.getObject(), permissionGrantingStrategy, authorizationStrategy)

    @Bean
    fun aclService(lookupStrategy: LookupStrategy, aclCache: EhCacheBasedAclCache): JdbcMutableAclService =
        JdbcMutableAclService(dataSource, lookupStrategy, aclCache)

    @Bean
    fun permissionEvaluator(aclService: JdbcMutableAclService): PermissionEvaluator =
        AclPermissionEvaluator(aclService)

    @Bean
    fun expressionHandlerAcl(
        aclService: JdbcMutableAclService,
        aclPermissionEvaluator: PermissionEvaluator
    ): MethodSecurityExpressionHandler =
        DefaultMethodSecurityExpressionHandler().apply {
            setPermissionEvaluator(aclPermissionEvaluator)
            setPermissionCacheOptimizer(AclPermissionCacheOptimizer(aclService))
        }
}
