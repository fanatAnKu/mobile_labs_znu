package com.example.kuzmenko.lb1.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.kuzmenko.lb1.model.Book
import com.example.kuzmenko.lb1.navigation.AddBookRoute
import com.example.kuzmenko.lb1.navigation.BookDetailsRoute
import com.example.kuzmenko.lb1.navigation.BookListRoute
import com.example.kuzmenko.lb1.ui.screens.AddBookScreen
import com.example.kuzmenko.lb1.ui.screens.BookDetailsScreen
import com.example.kuzmenko.lb1.ui.screens.BookListScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

private fun toggleBookRead(
    books: List<Book>,
    bookId: Int
): List<Book> {
    return books.map { book ->
        if (book.id == bookId) {
            book.copy(isRead = !book.isRead)
        } else {
            book
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookTrackerApp(bookViewModel: BookViewModel = viewModel(factory =
    BookViewModel.Factory
)) {

    val uiState by bookViewModel
        .uiState
        .collectAsStateWithLifecycle()

    val books = uiState.books

    val backStack = rememberNavBackStack(BookListRoute)

    NavDisplay(
        backStack = backStack,
        onBack = {
            backStack.removeLastOrNull()
        },
        entryProvider = entryProvider {
            entry<BookListRoute> {
                BookListScreen(
                    books = books,
                    onBookClick = { bookId ->
                        backStack.add(BookDetailsRoute(bookId = bookId))
                    },
                    onAddBook = {
                        backStack.add(AddBookRoute)
                    }
                )
            }
            entry<AddBookRoute> {
                AddBookScreen(
                    onCancel = {
                        backStack.removeLastOrNull()
                    },
                    onAddBook = {
                            title,
                            author,
                            description,
                            isRead ->

                        val nextId =
                            (books.maxOfOrNull {
                                it.id
                            } ?: 0) + 1

                        bookViewModel.addBook(
                            title = title,
                            author = author,
                            description = description,
                            isRead = isRead
                        )

                        backStack.removeLastOrNull()
                    }
                )
            }
            entry<BookDetailsRoute> { route ->
                val book = books.find { it.id == route.bookId }
                BookDetailsScreen(
                    book = book,
                    onBack = {
                        backStack.removeLastOrNull()
                    },
                    onToggleRead = {
                        bookViewModel.toggleBookRead(
                            route.bookId
                        )
                    }
                )
            }
        }
    )
}
