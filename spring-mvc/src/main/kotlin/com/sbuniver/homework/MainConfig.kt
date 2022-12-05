package com.sbuniver.homework

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@Configuration
@ServletComponentScan("com.sbuniver.homework")
class MainConfig