package com.example.springmvc.config

import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.Configuration


@Configuration
@ServletComponentScan(
    "com.example.springmvc.servlet",
    "com.example.springmvc.filter"
)
class ApplicationConfig