package com.example.kuzmenko.lb1.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BooksResponseDto(
    val results: List<BookDto>
)