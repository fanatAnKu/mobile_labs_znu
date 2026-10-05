package com.example.kuzmenko.lb1.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object BookListRoute : NavKey{}
@Serializable
data object AddBookRoute : NavKey{}

@Serializable
data class BookDetailsRoute(
    val bookId: Int
) : NavKey{}