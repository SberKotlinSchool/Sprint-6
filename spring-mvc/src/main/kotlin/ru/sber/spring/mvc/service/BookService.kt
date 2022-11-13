package ru.sber.spring.mvc.service

import org.springframework.stereotype.Service
import ru.sber.spring.mvc.model.Book
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Service
class BookService {
    private var atomicId: AtomicInteger = AtomicInteger()
    private val books: ConcurrentHashMap<Int, Book> = ConcurrentHashMap()

    @Synchronized fun add(book: Book) {
        var id = atomicId.addAndGet(1)
        book.id = id
        books.putIfAbsent(id, book)
    }

    @Synchronized
    fun update(book: Book) {
        books.putIfAbsent(book.id!!, book)
    }

    fun getBooks(): List<Book> {
        return books.values.toList()
    }

    fun getById(id: Int): Book? {
        return books[id]
    }

    fun delete(id: Int): Boolean {
        books.remove(id)
        return true
    }

}