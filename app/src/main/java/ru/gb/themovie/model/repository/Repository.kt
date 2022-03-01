package ru.gb.themovie.model.repository

import retrofit2.Call
import ru.gb.themovie.model.Movie
import ru.gb.themovie.model.pojo.MovieDetailModel
import ru.gb.themovie.model.pojo.MovieModel
import ru.gb.themovie.model.pojo.ResultMovieList

interface Repository {
    fun getMoviesFromServer(): Call<ResultMovieList>
    fun getMovieDetail(id: Int): Call<MovieDetailModel>
    fun getOnTvPopularMovies(): Call<ResultMovieList>
}