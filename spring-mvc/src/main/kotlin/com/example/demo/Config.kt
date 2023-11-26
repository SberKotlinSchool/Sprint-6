package com.example.demo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(UserConfigurationProperties::class)
class DemoConfiguration

@ConfigurationProperties(prefix = "user")
data class UserConfigurationProperties @ConstructorBinding constructor(
    val username: String,
    val password: String
)