package com.example.kuzmenko.lb1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kuzmenko.lb1.R

@Composable
fun AddBookForm(
    onAddBook: (
        title: String,
        author: String,
        description: String?,
        isRead: Boolean
    ) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember {
        mutableStateOf(value = "")
    }

    var author by remember {
        mutableStateOf(value = "")
    }

    var description by remember {
        mutableStateOf(value = "")
    }

    var isRead by remember {
        mutableStateOf(value = false)
    }

    val isFormValid =
        title.isNotBlank() &&
                author.isNotBlank()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = {
                Text(stringResource(R.string.book_title))
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = author,
            onValueChange = { author = it },
            label = {
                Text(stringResource(R.string.book_author))
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = {
                Text(stringResource(R.string.book_description))
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 4
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(stringResource(R.string.book_is_read))
            Checkbox(
                checked = isRead,
                onCheckedChange = { isRead = it }
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onCancel
            ) {
                Text(stringResource(R.string.cancel))
            }

            Button(
                onClick = {
                    onAddBook(
                        title,
                        author,
                        description.takeIf { it.isNotBlank() },
                        isRead
                    )
                },
                enabled = isFormValid
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}