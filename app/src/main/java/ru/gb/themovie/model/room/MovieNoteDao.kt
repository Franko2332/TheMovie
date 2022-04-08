package ru.gb.themovie.model.room

import androidx.room.*

@Dao
interface MovieNoteDao {

    @Query("SELECT * FROM MovieNoteEntity")
    fun all(): List<MovieNoteEntity>

    @Query("SELECT * FROM MovieNoteEntity WHERE movieId LIKE :movieId")
    fun getNoteByMovieId(movieId: Int): MovieNoteEntity

    @Query("SELECT EXISTS (SELECT * FROM MovieNoteEntity WHERE movieId = :movieId)")
    fun isExists(movieId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: MovieNoteEntity)

    @Update
    fun update(entity: MovieNoteEntity)

    @Delete
    fun delete(entity: MovieNoteEntity)
}