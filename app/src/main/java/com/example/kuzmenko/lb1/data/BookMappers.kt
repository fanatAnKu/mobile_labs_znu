package com.example.kuzmenko.lb1.data

import com.example.kuzmenko.lb1.data.local.BookEntity
import com.example.kuzmenko.lb1.data.remote.dto.BookDto
import com.example.kuzmenko.lb1.model.Book

fun BookEntity.toBook(): Book =
    Book(
        id = id,
        title = title,
        author = author,
        description = description,
        isRead = isRead
    )

fun Book.toEntity(): BookEntity =
    BookEntity(
        id = id,
        title = title,
        author = author,
        description = description,
        isRead = isRead
    )


fun BookDto.toBook(): Book {

    val author = authors
        .joinToString(separator = ", ")
        {
            it.name
        }.ifBlank {
            "Unknown author"
        }

    return Book(
        id = id,
        title = title,
        author = author,
        description = summaries.firstOrNull(),
        isRead = false
    )
}