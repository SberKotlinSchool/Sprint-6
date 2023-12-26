package addressbook.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : GlobalMethodSecurityConfiguration() {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeRequests { authorize ->
                authorize
                    .antMatchers("/api/app/**").hasAnyRole("ROLE_ADMIN", "ROLE_API")
                    .antMatchers("/app/**").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
                    .anyRequest().authenticated()
            }
            .httpBasic()

        return http.build()
    }


}