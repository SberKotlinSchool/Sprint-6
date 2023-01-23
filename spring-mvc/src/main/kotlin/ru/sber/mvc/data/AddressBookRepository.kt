package ru.sber.mvc.data

import org.springframework.data.jpa.repository.JpaRepository

interface AddressBookRepository : JpaRepository<Record, Long>
