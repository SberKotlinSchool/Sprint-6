package ru.sber.database

import mu.KLogging
import org.springframework.stereotype.Service
import ru.sber.dto.AddressDto
import ru.sber.dto.QueryDto
import java.util.concurrent.ConcurrentHashMap

@Service
class DbService {

    companion object : KLogging()

    private val db: ConcurrentHashMap<Long, AddressDto> = ConcurrentHashMap()

    private val users: ConcurrentHashMap<String, String> = createStaticMap()

    fun getUsers(): Map<String, String> {
        return users
    }

    private fun createStaticMap(): ConcurrentHashMap<String, String> {
        val map = ConcurrentHashMap<String, String>()
        map["user1"] = "pass1"
        map["user2"] = "pass2"
        map["1"] = "1"
        return map
    }

    fun createAddress(addressDto: AddressDto): AddressDto {
        val newKey = db.keys().asSequence().maxOrNull()?.plus(1) ?: 1
        addressDto.id = newKey
        db.put(newKey, addressDto)

        return addressDto
    }

    fun findAll(): List<AddressDto> {
        return db.values.sortedBy { it.id }
    }

    fun findByQuery(query: QueryDto?): List<AddressDto> {
        return if (query?.byName != null) {
            db.values.filter { it.name.contains(query.byName, true) }.sortedBy { it.id }
        } else {
            listOf()
        }
    }

    fun findById(id: Long): AddressDto? {
        return db[id]
    }

    fun edit(id: Long, addressDto: AddressDto): Boolean {
        if (db.containsKey(id)) {
            db[id] = addressDto
            logger.info("edit - ok")
            return true
        }
        logger.info("edit - not ok")
        return false
    }

    fun delete(id: Long): Boolean {
        return db.remove(id)?.let { true } ?: false
    }

    fun deleteAll() {
        db.clear()
    }


}