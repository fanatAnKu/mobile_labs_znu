package com.example.kuzmenko.lb1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kuzmenko.lb1.R
import com.example.kuzmenko.lb1.model.Book
import com.example.kuzmenko.lb1.ui.components.BookCard



@Composable
fun BookList(
    books: List<Book>,
    modifier: Modifier = Modifier
) {
    if (books.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.empty_books)
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding =
                PaddingValues(all = 16.dp),
            verticalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = books,
                key = { book -> book.id }
            ) { book ->
                BookCard(book)
            }
        }
    }
}