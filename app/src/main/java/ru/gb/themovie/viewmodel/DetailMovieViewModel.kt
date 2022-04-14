package ru.gb.themovie.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.themovie.model.AppState
import ru.gb.themovie.model.AppState.Error
import ru.gb.themovie.model.pojo.MovieDetailModel
import ru.gb.themovie.model.pojo.ResultMovieCredits
import ru.gb.themovie.model.repository.Repository
import ru.gb.themovie.model.repository.RepositoryImpl


class DetailMovieViewModel() : ViewModel() {
    private val liveData: MutableLiveData<AppState> = MutableLiveData()
    private val movieCreditsLiveData: MutableLiveData<AppState> = MutableLiveData()
    private val repo: Repository = RepositoryImpl()

    fun getMovie(_id: Int): MutableLiveData<AppState> {
        loadMovie(_id)
        return liveData
    }

    fun getMovieCredits(_id: Int): MutableLiveData<AppState> {
        loadMovieCredits(_id)
        return movieCreditsLiveData
    }

    private fun loadMovieCredits(_id: Int) {
        repo.getMovieCredits(_id).enqueue(object : Callback<ResultMovieCredits> {
            override fun onResponse(
                call: Call<ResultMovieCredits>,
                response: Response<ResultMovieCredits>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.e("OK", response.body()!!.cast.toString())
                        response.body()?.let { _response ->
                            _response.cast.let {
                                movieCreditsLiveData.postValue(AppState.SuccessMovieCredits(it))
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResultMovieCredits>, t: Throwable) {
                Log.e("ERROR", "ERROR MOVIE CREDITS")
                movieCreditsLiveData.postValue(Error)
            }

        })
    }


    private fun loadMovie(_id: Int) {
        repo.getMovieDetail(_id).enqueue(object : Callback<MovieDetailModel> {
            override fun onResponse(
                call: Call<MovieDetailModel>,
                response: Response<MovieDetailModel>
            ) {
                Log.e("OK", response.raw().request().url().toString())
                when (response.code()) {
                    200 -> {
                        liveData.postValue(
                            AppState.SuccessDetailMovie(
                                response.body()
                                        as MovieDetailModel
                            )
                        )
                        val obj = response.body() as MovieDetailModel
                        Log.e("OK", obj.title)
                    }
                }
            }

            override fun onFailure(call: Call<MovieDetailModel>, t: Throwable) {
                Log.e("ERROR", "ERROR")
                liveData.postValue(Error)
            }
        })
    }

    fun clearLiveData() {
        liveData.postValue(AppState.Loading)
        movieCreditsLiveData.postValue(AppState.Loading)
    }

}

