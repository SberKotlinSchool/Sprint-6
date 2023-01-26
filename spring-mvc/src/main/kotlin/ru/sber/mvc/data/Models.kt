package ru.sber.mvc.data

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "address_book_user")
class User(
    @Column(nullable = false, unique = true)
    val username: String,

    var password: String,

    var groups: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long
)

@Entity
@Table(name = "address_book_record")
class Record(
    val name: String,

    val phone: String,

    val address: String,

    val description: String = "",

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long
)
