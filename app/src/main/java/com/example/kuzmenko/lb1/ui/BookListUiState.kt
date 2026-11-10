package com.example.kuzmenko.lb1.ui

import com.example.kuzmenko.lb1.model.Book

data class BookListUiState(
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)