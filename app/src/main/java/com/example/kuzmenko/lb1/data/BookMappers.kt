package com.example.kuzmenko.lb1.data

import com.example.kuzmenko.lb1.data.local.BookEntity
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