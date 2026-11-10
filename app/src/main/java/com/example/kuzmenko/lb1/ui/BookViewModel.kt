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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class BookViewModel(private val repository: BookRepository) : ViewModel(){


    private data class RefreshUiState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    private val _refreshState = MutableStateFlow(value = RefreshUiState())

    val uiState: StateFlow<BookListUiState> = combine(
        flow = repository.observeBooks(),
        flow2 = _refreshState
    ) { books, refreshState ->
        BookListUiState(
            books = books,
            isLoading = refreshState.isLoading,
            errorMessage = refreshState.errorMessage
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = BookListUiState()
        )

    fun refreshBooks() {
        viewModelScope.launch {
            _refreshState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val result = repository.refreshBooks()

                if (!result.isSuccess) {
                    _refreshState.update { it.copy(errorMessage = result.errorMessage) }
                }
            } finally {
                _refreshState.update { it.copy(isLoading = false) }
            }
        }
    }

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