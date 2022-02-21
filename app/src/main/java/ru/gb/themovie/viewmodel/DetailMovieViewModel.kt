package ru.gb.themovie.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.gb.themovie.model.AppState
import ru.gb.themovie.model.Repository
import ru.gb.themovie.model.RepositoryImpl

class DetailMovieViewModel (private val liveData: MutableLiveData<AppState> = MutableLiveData()) : ViewModel() {
    private lateinit var repo: Repository

    public fun initRepo(context: Context) {
        repo = RepositoryImpl(context)
    }

    public fun getMovie(_id: Int): MutableLiveData<AppState> {
            loadMovies(_id)
        return liveData

    }

    public fun getLiveData(): MutableLiveData<AppState> =  liveData

    private fun loadMovies(_id: Int) = liveData.postValue(AppState.SuccessDetailMovie(_id, repo))

}