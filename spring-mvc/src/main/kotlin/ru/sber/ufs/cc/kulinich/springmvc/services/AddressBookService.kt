package ru.sber.ufs.cc.kulinich.springmvc.services

import ch.qos.logback.core.net.server.Client
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.sber.ufs.cc.kulinich.springmvc.filters.LoggingFilter
import ru.sber.ufs.cc.kulinich.springmvc.models.AddressBookModel
import ru.sber.ufs.cc.kulinich.springmvc.repositories.AdressBookRepository

@Service
class AddressBookService {

    private val logger = LoggerFactory.getLogger(LoggingFilter::class.java)
    lateinit var addrBookDAO : AdressBookRepository
    @Autowired set

    fun add(contanct : AddressBookModel) {
        logger.info("adding ${contanct}")
        addrBookDAO.add(contanct)
    }

    fun getAll() : List<AddressBookModel> {
        return addrBookDAO.getAll().map { it.second }
    }

    fun getById(id : Int) : AddressBookModel? {
        logger.info("Getting ${id}")
        return addrBookDAO.getById(id)
    }

    fun edit(id : Int, contanct: AddressBookModel){
        logger.info("Edditing  ${contanct} by id $id")
        addrBookDAO.edit(id, contanct)
    }

    fun delete(id: Int) {
        logger.info("Deleting by id ${id}")
        addrBookDAO.delete(id)
    }

}