package ru.gb.themovie.model.repository

import retrofit2.Call
import ru.gb.themovie.model.pojo.MovieDetailModel
import ru.gb.themovie.model.pojo.PersonDetailModel
import ru.gb.themovie.model.pojo.ResultMovieCredits
import ru.gb.themovie.model.pojo.ResultMovieList

interface Repository {
    fun getMoviesFromServer(): Call<ResultMovieList>
    fun getMovieDetail(id: Int): Call<MovieDetailModel>
    fun getOnTvPopularMovies(): Call<ResultMovieList>
    fun getPerson(id: Int): Call<PersonDetailModel>
    fun getMovieCredits(id: Int): Call<ResultMovieCredits>
}