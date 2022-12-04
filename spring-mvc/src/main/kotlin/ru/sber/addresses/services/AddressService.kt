package ru.sber.addresses.services

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.sber.addresses.dto.Address
import ru.sber.addresses.requests.CreateAddressRq

@Service
class AddressService {
    companion object {
        private val addressBook = mutableMapOf<Long, Address>()
        private var counter = 1L
        private val logger = LoggerFactory.getLogger(AddressService::class.java)
    }

    /**
     * Создание записи в адресной книге
     * @param address Запрос на создание записи в адресной книге
     * @return Запись типа "Адрес"
     */
    fun createAddress(address: CreateAddressRq): Address {
        val newAddress = Address(counter, address.fullName, address.postAddress, address.phoneNumber, address.email)
        addressBook[counter] = newAddress
        counter++
        logger.info("Создана запись в адресной книге с id = ${newAddress.id}")
        return newAddress
    }

    /**
     * Получение адресов из адресной книги
     * @param addressId ID записи
     * @return Список адресов
     */
    fun getAddresses(addressId: Long? = null): List<Address?> {
        return if (addressId == null) {
            logger.info("Получение всех записей в адресной книге")
            addressBook.values.toList()
        } else {
            logger.info("Получение в адресной книге записи с id = $addressId")
            addressBook.filter { it.key == addressId }.values.toList()
        }
    }

    /**
     * Обновление адреса
     * @param addressId ID записи
     * @param address Адрес
     * @return Запись типа "Адрес"
     */
    fun updateAddress(addressId: Long, address: CreateAddressRq): Address? {
        return if (addressBook.containsKey(addressId)) {
            logger.info("Обновлена запись в адресной книге с id = $addressId")
            val newAddress = Address(addressId, address.fullName, address.postAddress, address.phoneNumber, address.email)
            addressBook[addressId] = newAddress
            newAddress
        } else {
            logger.error("Не найдена запись в адресной книге с id = $addressId")
            null
        }
    }

    /**
     * Получение адресов из адресной книги
     * @param addressId ID записи
     */
    fun deleteAddress(addressId: Long): Address? {
        val result = addressBook.remove(addressId)
        if (result != null) {
            logger.info("Удалена запись в адресной книге с id = $addressId")
        } else {
            logger.error("Не найдена запись в адресной книге с id = $addressId")
        }
        return result
    }
}