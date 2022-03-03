package ru.gb.themovie.viewmodel.databinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.themovie.model.AppState
import ru.gb.themovie.model.pojo.ResultMovieList
import ru.gb.themovie.model.repository.Repository

class PopularMovieViewModel(val repo: Repository) : ViewModel() {
    private val dataMoviesFromServer = MutableLiveData<List<ItemViewModel>>()
    private val dataOnTvMoviesFromServer = MutableLiveData<List<ItemViewModel>>()
    private val appStateLiveData = MutableLiveData<AppState>()

    init {
        loadDataFromServer()
    }

    private fun loadDataFromServer() {
        appStateLiveData.postValue(AppState.Loading)
        repo.getMoviesFromServer().enqueue(object : Callback<ResultMovieList> {
            override fun onResponse(
                call: Call<ResultMovieList>,
                response: Response<ResultMovieList>
            ) {
                when (response.code()) {
                    200 -> {
                        response?.body()?.let {
                            dataMoviesFromServer.postValue(createViewDataMoviesFromServer(it))
                            appStateLiveData.postValue(AppState.Success)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResultMovieList>, t: Throwable) {
                appStateLiveData.postValue(AppState.Error)
            }
        })

        repo.getOnTvPopularMovies().enqueue(object : Callback<ResultMovieList> {
            override fun onResponse(
                call: Call<ResultMovieList>,
                response: Response<ResultMovieList>
            ) {
                when (response.code()) {
                    200 -> {
                        response?.body()?.let {
                            dataOnTvMoviesFromServer.postValue(createViewDataMoviesFromServer(it))
                            appStateLiveData.postValue(AppState.Success)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResultMovieList>, t: Throwable) {
                appStateLiveData.postValue(AppState.Error)
            }
        })
    }

    public fun getAppStateLiveData(): LiveData<AppState> {
        loadDataFromServer()
        return appStateLiveData
    }

    public fun getDataMoviesFromServer(): LiveData<List<ItemViewModel>> = dataMoviesFromServer

    public fun getDataOnTvMoviesFromServer(): LiveData<List<ItemViewModel>> = dataOnTvMoviesFromServer

    private fun createViewDataMoviesFromServer(resultMovieList: ResultMovieList): List<ItemViewModel> {
        val moviesViewData = mutableListOf<ItemViewModel>()
        resultMovieList.results.forEach {
            moviesViewData.add(ItemPopularMovieFromServerViewModel(it))
        }
        return moviesViewData
    }


}