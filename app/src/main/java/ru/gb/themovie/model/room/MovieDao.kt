package ru.gb.themovie.model.room

import androidx.room.*

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieEntity")
    fun allMovies(): List<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE id LIKE :movieId")
    fun getMovieById(movieId: Int): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieEntity: MovieEntity)

    @Delete
    fun delete(movieEntity: MovieEntity)

    @Update
    fun update(movieEntity: MovieEntity)

    @Query("SELECT EXISTS (SELECT * FROM MovieEntity WHERE id = :movieId)")
    fun isExists(movieId: Int): Boolean
}