package ru.gb.themovie.model.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.gb.themovie.model.pojo.MovieDetailModel
import ru.gb.themovie.model.pojo.ResultMovieList

interface MovieDatabaseApi {
    @GET ("movie/popular")
    fun getMovieList(@Query("api_key") apiKey: String,
                     @Query ("language") language: String,
                     @Query ("page") page: String): Call<ResultMovieList>

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movie_id: Int,
                       @Query("api_key") apiKey: String,
                       @Query ("language") language: String,): Call<MovieDetailModel>

    @GET ("tv/popular")
    fun getOnTvMovieList(@Query("api_key") apiKey: String,
                     @Query ("language") language: String,
                     @Query ("page") page: String): Call<ResultMovieList>
}