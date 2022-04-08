package ru.gb.themovie.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.themovie.model.AppState
import ru.gb.themovie.model.AppState.Error
import ru.gb.themovie.model.pojo.MovieDetailModel
import ru.gb.themovie.model.repository.Repository
import ru.gb.themovie.model.repository.RepositoryImpl


class DetailMovieViewModel () : ViewModel() {
    private val liveData: MutableLiveData<AppState> = MutableLiveData()
    private lateinit var repo: Repository

    public fun initRepo(context: Context) {
        repo = RepositoryImpl(context)
    }

    public fun getMovie(_id: Int): MutableLiveData<AppState> {
            loadMovie(_id)
        return liveData

    }

    public fun getLiveData(): MutableLiveData<AppState> =  liveData

    private fun loadMovie(_id: Int) {
        repo.getMovieDetail(_id).enqueue(object : Callback<MovieDetailModel> {
            override fun onResponse(call: Call<MovieDetailModel>,
                                    response: Response<MovieDetailModel>) {
                Log.e("OK", response.raw().request().url().toString())
                when (response.code()){
                    200 -> { liveData.postValue(AppState.SuccessDetailMovie(response.body()
                                as MovieDetailModel))
                        var obj = response.body() as MovieDetailModel
                        Log.e("OK", obj.title)
                    }
                }
            }

            override fun onFailure(call: Call<MovieDetailModel>, t: Throwable) {
                Log.e("ERROR", "ERROR")
               liveData.postValue(AppState.Error)
            }
        })
    }

    public fun clearLiveData(){
        liveData.postValue(AppState.Loading)
    }

}

