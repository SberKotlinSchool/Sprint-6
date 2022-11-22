package ru.sber.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.ConfigurableEnvironment


@Configuration
@ComponentScan("ru.sber.services")
class ServicesConfig @Autowired constructor(env: ConfigurableEnvironment) {

    init {
        env.setActiveProfiles("prod")
    }

}


