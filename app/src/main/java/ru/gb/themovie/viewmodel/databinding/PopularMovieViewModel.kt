package ru.gb.themovie.viewmodel.databinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.themovie.App
import ru.gb.themovie.model.AppState
import ru.gb.themovie.model.pojo.ResultMovieList
import ru.gb.themovie.model.repository.Repository
import ru.gb.themovie.model.room.MovieDataBase
import ru.gb.themovie.model.room.MovieEntity

class PopularMovieViewModel(val repo: Repository) : ViewModel() {
    private var dataBase: MovieDataBase = App.getMovieDatabase()
    private val dataMoviesFromServer = MutableLiveData<List<ItemViewModel>>()
    private val dataOnTvMoviesFromServer = MutableLiveData<List<ItemViewModel>>()
    private val appStateLiveData = MutableLiveData<AppState>()
    private var adult: Boolean = false

    init {
        loadDataFromServer()
    }

    public fun setAdult(_adult: Boolean) {
        adult = _adult
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
                            saveMoviesToDatabase(it)
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

    public fun getDataOnTvMoviesFromServer(): LiveData<List<ItemViewModel>> =
        dataOnTvMoviesFromServer

    private fun createViewDataMoviesFromServer(resultMovieList: ResultMovieList): List<ItemViewModel> {
        val moviesViewData = mutableListOf<ItemViewModel>()
        if (!adult) {
            resultMovieList.results.forEach {
                if (it.adult == false || it.adult == null)
                    moviesViewData.add(ItemPopularMovieFromServerViewModel(it))
            }
        } else
            resultMovieList.results.forEach {
                moviesViewData.add(ItemPopularMovieFromServerViewModel(it))
            }
        return moviesViewData
    }

    private fun saveMoviesToDatabase(resultMovieList: ResultMovieList) {

        resultMovieList.results.forEach {
            if (dataBase.movieDao().isExists(it.id!!)) {
                dataBase.movieDao().update(
                    MovieEntity(
                        it.id!!, it.title!!, it.overview!!, it.release_date!!,
                        it.poster_path!!
                    )
                )
            } else {
                dataBase.movieDao().insert(
                    MovieEntity(
                        it.id!!, it.title!!, it.overview!!, it.release_date!!,
                        it.poster_path!!
                    )
                )
            }
        }
    }

}
