package ru.sber.agadressbook.config

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("ru.sber.agadressbook")
@ServletComponentScan("ru.sber.agadressbook.servlets", "ru.sber.agadressbook.filters")
class AddressBookConfig