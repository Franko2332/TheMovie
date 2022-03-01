package ru.gb.themovie.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.themovie.model.AppState
import ru.gb.themovie.model.repository.Repository
import ru.gb.themovie.model.repository.RepositoryImpl

class MainViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData()) : ViewModel() {
    private lateinit var repo: Repository

    public fun initRepo(context: Context) {
        repo = RepositoryImpl(context)
    }

    public fun getData(): MutableLiveData<AppState> {
        loadMovies()
        return liveData
    }

    private fun loadMovies() = liveData.postValue(AppState.Success(repo))



}