package com.example.kuzmenko.lb1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kuzmenko.lb1.BookTrackerApplication
import com.example.kuzmenko.lb1.data.BookRepository
import com.example.kuzmenko.lb1.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class BookViewModel(private val repository: BookRepository) : ViewModel(){

    val uiState: StateFlow<BookListUiState> =
        repository
            .observeBooks()
            .map { books ->

                BookListUiState(
                    books = books
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
                initialValue = BookListUiState()
            )

    fun addBook(
        title: String,
        author: String,
        description: String?,
        isRead: Boolean
    ) {
        val book = Book(
            id = 0,
            title = title,
            author = author,
            description = description,
            isRead = isRead
        )

        viewModelScope.launch {
            repository.addBook(
                book
            )
        }
    }

    fun toggleBookRead(
        bookId: Int
    ) {
        val book =
            uiState
                .value
                .books
                .find {
                    it.id == bookId
                }
                ?: return

        val updatedBook = book.copy(
            isRead = !book.isRead
        )

        viewModelScope.launch {
            repository.updateBook(
                updatedBook
            )
        }
    }
    companion object {

        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = this[APPLICATION_KEY] as BookTrackerApplication
                    val repository = application.container.bookRepository

                    BookViewModel(
                        repository = repository
                    )
                }
            }
    }
}