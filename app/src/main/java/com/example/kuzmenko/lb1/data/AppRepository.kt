package com.example.kuzmenko.lb1.data

import com.example.kuzmenko.lb1.data.local.BookDao
import com.example.kuzmenko.lb1.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.kuzmenko.lb1.data.remote.BookApiService
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException

class BookRepository(
    private val bookDao: BookDao,
    private val bookApiService: BookApiService
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


    suspend fun refreshBooks(): RefreshResult {
        return try {
            val response = bookApiService.getBooks()
            val entities = response.results.map { it.toBook().toEntity() }
            bookDao.upsertAll(books = entities)

            RefreshResult(isSuccess = true)
        } catch (e: IOException) {
            RefreshResult(isSuccess = false, errorMessage = "Не вдалося підключитися до сервера")
        } catch (e: HttpException) {
            RefreshResult(isSuccess = false, errorMessage = "Сервер повернув помилку ${e.code()}")
        } catch (e: SerializationException) {
            RefreshResult(isSuccess = false, errorMessage = "Некоректна відповідь сервера")
        }
    }
}