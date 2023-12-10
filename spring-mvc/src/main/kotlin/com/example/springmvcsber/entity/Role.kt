package com.example.springmvcsber.entity

import org.springframework.security.core.GrantedAuthority


enum class Role : GrantedAuthority {
    API, ADMIN;

    override fun getAuthority(): String {
        return name
    }
}