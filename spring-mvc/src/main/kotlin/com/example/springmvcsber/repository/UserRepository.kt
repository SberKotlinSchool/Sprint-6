package com.example.springmvcsber.repository

import com.example.springmvcsber.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByName(name: String): User?
}