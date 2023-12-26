package com.example.demospringsecurity.entity

import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(name = "username", length = 50, nullable = false)
    val username: String = "",

    @Column(name = "password", length = 500, nullable = false)
    val password: String = "",

    @Column(name = "enabled", nullable = false)
    val enabled: Boolean = false
)

@Entity
@Table(name = "authorities")
data class Authority(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    val user: User? = null,

    @Column(name = "authority", length = 50, nullable = false)
    val authority: String? = null
)