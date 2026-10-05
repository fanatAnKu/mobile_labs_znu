package com.example.kuzmenko.lb1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddBookScreen(
    onAddBook: (title: String, author: String, description: String?, isRead: Boolean) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var title by rememberSaveable { mutableStateOf("") }
    var author by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var isRead by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextButton(
            onClick = {
                title = ""
                author = ""
                description = ""
                isRead = false
            }
        ) {
            Text("Очистити форму")
        }

        AddBookForm(
            title = title,
            author = author,
            description = description,
            isRead = isRead,
            onTitleChange = { title = it },
            onAuthorChange = { author = it },
            onDescriptionChange = { description = it },
            onReadChange = { isRead = it },
            onSave = {
                onAddBook(
                    title.trim(),
                    author.trim(),
                    description
                        .trim()
                        .takeIf { it.isNotEmpty() },
                    isRead
                )
            },
            onCancel = onCancel,
            modifier = Modifier
        )
    }
}