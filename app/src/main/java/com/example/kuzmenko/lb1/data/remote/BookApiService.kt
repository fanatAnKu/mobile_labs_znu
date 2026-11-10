package com.example.kuzmenko.lb1.data.remote

import com.example.kuzmenko.lb1.data.remote.dto.BooksResponseDto
import retrofit2.http.GET

interface BookApiService {

    @GET(value = "books/")
    suspend fun getBooks(): BooksResponseDto
}