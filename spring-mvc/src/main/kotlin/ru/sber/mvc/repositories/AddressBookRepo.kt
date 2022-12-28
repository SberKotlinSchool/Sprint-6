package ru.sber.mvc.repositories

import org.springframework.stereotype.Repository
import ru.sber.mvc.models.AddressRow
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Repository
class AddressBookRepo : AddressBookRepoControllable {

    private var sequence = AtomicInteger(0)

    private var table = ConcurrentHashMap(
        mapOf(
            sequence.addAndGet(0) to AddressRow(id = 0, name = "John", phone = "7-924-202-23-01", descr = "Bad man"),
            sequence.incrementAndGet() to AddressRow(id = 1, name = "Billy", phone = "7-924-202-35-02", descr = "Good man"),
            sequence.incrementAndGet() to AddressRow(id = 2, name = "Israel", phone = "7-924-202-35-10", descr = "Ugly man")
        )
    )

    override fun getList(name: String?, phone: String?): List<AddressRow> {
        return table.values.toList()
            .filter { name.isNullOrEmpty() || it.name == name }
            .filter { phone.isNullOrEmpty() || it.phone == phone }
    }

    override fun getById(id: Int): AddressRow? {
        return table[id]
    }

    override fun insert(row: AddressRow) {
        var id = row.id

        if (id == null) {
            id = sequence.incrementAndGet()
            row.id = id
        }
        table[id] = row
    }

    override fun update(row: AddressRow) {
        table[row.id] = row
    }

    override fun delete(id: Int) {
        table.remove(id)
    }
}