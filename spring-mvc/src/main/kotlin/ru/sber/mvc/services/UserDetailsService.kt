package ru.sber.mvc.services

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.sber.mvc.data.User
import ru.sber.mvc.data.UserRepository

@Service
class UserDetailsServiceImpl(val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByUsername(username)?.toUserDetails() ?: throw IllegalArgumentException()
}

private fun User.toUserDetails(): UserDetails =
    object : UserDetails {

        override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
            this@toUserDetails.groups
                .split(",").asSequence()
                .map(::SimpleGrantedAuthority)
                .toMutableList()

        override fun getPassword(): String = this@toUserDetails.password

        override fun getUsername(): String = this@toUserDetails.username

        override fun isAccountNonExpired(): Boolean = true

        override fun isAccountNonLocked(): Boolean = true

        override fun isCredentialsNonExpired(): Boolean = true

        override fun isEnabled(): Boolean = true
    }
