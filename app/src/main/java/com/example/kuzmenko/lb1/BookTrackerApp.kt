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
import com.example.kuzmenko.lb1.model.Book
import com.example.kuzmenko.lb1.ui.screens.AddBookForm
import com.example.kuzmenko.lb1.ui.screens.BookList

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

    var showAddForm by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (showAddForm) {
                            stringResource(R.string.add_book_title)
                        } else {
                            stringResource(R.string.books_title)
                        }
                    )
                }
            )
        },
        floatingActionButton = {
            if (!showAddForm) {
                FloatingActionButton(
                    onClick = {
                        showAddForm = true
                    }
                ) {
                    Text("+")
                }
            }
        }
    ) { innerPadding ->
        if (showAddForm) {
            AddBookForm(
                modifier = Modifier.padding(innerPadding),
                onCancel = {
                    showAddForm = false
                },
                onAddBook = { title, author, description, isRead ->
                    val nextId = (books.maxOfOrNull { it.id } ?: 0) + 1
                    val newBook = Book(
                        id = nextId,
                        title = title,
                        author = author,
                        description = description?.takeIf { it.isNotBlank() },
                        isRead = isRead
                    )
                    books = books + newBook
                    showAddForm = false
                }
            )
        } else {
            BookList(
                books = books,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}