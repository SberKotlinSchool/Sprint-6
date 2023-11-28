package ru.sber.config

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import ru.sber.model.User
import ru.sber.repository.UserRepository

@Configuration
@ComponentScan("ru.sber")
class RepositoryConfig {

    @Bean
    @Primary
    // TODO когда подключим реальную базу, эти конфиги переедут в тесты
    fun initUserDatabase(@Autowired repository: UserRepository): UserRepository {
        repository.createUser(User("login", "password"))
        repository.createUser(User("u", "u"))
        log.info { "${repository.getByLogin("login")} ${repository.getByLogin("u")} " }
        return repository
    }

    companion object {
        val log = KotlinLogging.logger {}
    }
}