package ru.sber.mvc.repositories

import ru.sber.mvc.models.AddressRow

interface AddressBookRepoControllable {

    // просмотр записей и поиск если будет переданы query параметры запроса
    fun getList(name: String?, phone: String?):List<AddressRow>

    // просмотр конкретной записи
    fun getById(id: Int): AddressRow?

    // вставить запись
    fun insert(row: AddressRow)

    // редактировать запись
    fun update(row: AddressRow)

    // удалить запись
    fun delete(id: Int)
}