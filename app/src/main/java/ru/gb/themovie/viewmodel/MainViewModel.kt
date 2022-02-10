package ru.gb.themovie.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.themovie.model.Movie
import ru.gb.themovie.model.Repository
import ru.gb.themovie.model.RepositoryImpl

class MainViewModel(private val liveData: MutableLiveData<ArrayList<Movie>> = MutableLiveData()) : ViewModel() {
    private lateinit var repo : Repository

    public fun initRepo(context: Context){
        repo = RepositoryImpl(context)
    }

    public fun getData() : MutableLiveData<ArrayList<Movie>>{
        loadMovies()
        return liveData
    }

    private fun loadMovies() {
        liveData.postValue(repo.getMoviesFromLocalStorage())
    }


}