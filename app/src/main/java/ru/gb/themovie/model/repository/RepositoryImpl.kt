package ru.gb.themovie.model.repository

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import retrofit2.Call
import ru.gb.themovie.BuildConfig

import ru.gb.themovie.R
import ru.gb.themovie.model.pojo.*
import ru.gb.themovie.model.retrofit.RetrofitService

class RepositoryImpl() : Repository {

    @SuppressLint("CheckResult")
    override fun getMoviesFromServer(): Call<ResultMovieList> = RetrofitService
        .getMovieDBApiService()
        .getMovieList(BuildConfig.THE_MOVIE_API_KEY, "ru", "1")

    @SuppressLint("CheckResult")
    override fun getMovieDetail(id: Int): Call<MovieDetailModel> = RetrofitService
        .getMovieDBApiService()
        .getMovieDetail(id, BuildConfig.THE_MOVIE_API_KEY, "ru")

    override fun getOnTvPopularMovies(): Call<ResultMovieList> = RetrofitService
        .getMovieDBApiService()
            .getOnTvMovieList(BuildConfig.THE_MOVIE_API_KEY, "ru", "1")

    override fun getPerson(id: Int): Call<PersonDetailModel> = RetrofitService
        .getMovieDBApiService()
        .getPerson(id, BuildConfig.THE_MOVIE_API_KEY, "ru")

    override fun getMovieCredits(id: Int): Call<ResultMovieCredits> = RetrofitService
        .getMovieDBApiService()
        .getMovieCredits(id, BuildConfig.THE_MOVIE_API_KEY, "ru")


}



