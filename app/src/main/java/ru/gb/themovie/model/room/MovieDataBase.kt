package ru.gb.themovie.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(MovieNoteEntity::class, MovieEntity::class), version = 1, exportSchema = false)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun movieNoteDao(): MovieNoteDao
    abstract fun movieDao(): MovieDao
}