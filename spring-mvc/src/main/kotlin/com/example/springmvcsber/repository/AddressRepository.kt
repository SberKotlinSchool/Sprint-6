package com.example.springmvcsber.repository

import com.example.springmvcsber.entity.Address
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface AddressRepository : JpaRepository<Address, Long> {
    fun findByCity(city: String) : List<Address>
}