package ru.sber.springmvc.domain

import java.util.concurrent.ConcurrentHashMap

data class AddressBook(val addressBook: ConcurrentHashMap<Long, Record>)

data class Record(val id: Long, val name: String, val address: String)
