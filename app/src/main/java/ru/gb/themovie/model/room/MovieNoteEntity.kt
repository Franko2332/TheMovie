package ru.gb.themovie.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieNoteEntity(
    @PrimaryKey
    val movieId: Int,
    val note: String?,
)

