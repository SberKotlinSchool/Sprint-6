package com.example.springmvcsber.entity

import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false, unique = true)
    val name: String,
    var passwd: String,
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_name")])
    @Enumerated(EnumType.STRING)
    val roles: Set<Role> = HashSet(),
    var expired: Boolean,
    var locked: Boolean,
    var credExpired: Boolean,
    var enabled: Boolean
) : UserDetails {


    override fun getAuthorities() = roles
    override fun getUsername(): String = name
    override fun getPassword(): String = passwd
    override fun isAccountNonExpired(): Boolean = !expired
    override fun isAccountNonLocked(): Boolean = !locked
    override fun isCredentialsNonExpired(): Boolean = !credExpired
    override fun isEnabled(): Boolean = enabled
}