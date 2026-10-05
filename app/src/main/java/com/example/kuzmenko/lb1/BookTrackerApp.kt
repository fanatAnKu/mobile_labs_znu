package com.example.kuzmenko.lb1

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.kuzmenko.lb1.model.Book
import com.example.kuzmenko.lb1.navigation.AddBookRoute
import com.example.kuzmenko.lb1.navigation.BookDetailsRoute
import com.example.kuzmenko.lb1.navigation.BookListRoute
import com.example.kuzmenko.lb1.ui.screens.AddBookForm
import com.example.kuzmenko.lb1.ui.screens.AddBookScreen
import com.example.kuzmenko.lb1.ui.screens.BookDetailsScreen
import com.example.kuzmenko.lb1.ui.screens.BookList
import com.example.kuzmenko.lb1.ui.screens.BookListScreen

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
fun BookTrackerApp() {
    var books by remember {
        mutableStateOf(
            listOf(
                Book(
                    id = 1,
                    title = "Clean Code",
                    author = "Robert C. Martin",
                    description = "A handbook of agile software craftsmanship.",
                    isRead = true
                ),
                Book(
                    id = 2,
                    title = "Kotlin in Action",
                    author = "Dmitry Jemerov, Svetlana Isakova",
                    description = null,
                    isRead = false
                ),
                Book(
                    id = 3,
                    title = "Refactoring",
                    author = "Martin Fowler",
                    description = "Improving the design of existing code.",
                    isRead = false
                )
            )
        )
    }

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
                    onAddBook = { title, author, description, isRead ->
                        val nextId = (books.maxOfOrNull { it.id } ?: 0) + 1
                        val newBook = Book(
                            id = nextId,
                            title = title,
                            author = author,
                            description = description,
                            isRead = isRead
                        )
                        books = books + newBook
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
                        books = toggleBookRead(
                            books = books,
                            bookId = route.bookId
                        )
                    }
                )
            }
        }
    )
}
