package ru.sber.AddressBook

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("ru.sber.AddressBook.")
@ServletComponentScan("ru.sber.AddressBook.Servlets", "ru.sber.AddressBook.Filters")
class Config