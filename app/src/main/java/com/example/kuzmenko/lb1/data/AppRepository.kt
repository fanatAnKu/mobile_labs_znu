package com.example.kuzmenko.lb1.data

import com.example.kuzmenko.lb1.data.local.BookDao
import com.example.kuzmenko.lb1.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepository(
    private val bookDao: BookDao
) {

    fun observeBooks(): Flow<List<Book>> =
        bookDao
            .observeAll()
            .map { entities ->
                entities.map {
                    it.toBook()
                }
            }

    suspend fun addBook(
        book: Book
    ) {
        bookDao.insert(
            book.toEntity()
        )
    }

    suspend fun updateBook(
        book: Book
    ) {
        bookDao.update(
            book.toEntity()
        )
    }
}