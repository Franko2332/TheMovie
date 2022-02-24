package ru.gb.themovie.viewmodel.databinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.gb.themovie.model.Movie
import ru.gb.themovie.model.Repository

class PopularMovieViewModel(val repo: Repository) : ViewModel() {
    private var dataMovies = MutableLiveData<List<ItemViewModel>>()
    private val dataSerials = MutableLiveData<List<ItemViewModel>>()


    init {
        loadData()
    }

    public fun getPopularMovieData(): LiveData<List<ItemViewModel>> = dataMovies

    public fun getPopularSerialsData(): LiveData<List<ItemViewModel>> = dataSerials


    private fun loadData() {
        viewModelScope.launch {
            val movies = repo.getMoviesFromLocalStorage()
            val serials = repo.getSerialsFromLocalStorage()
            dataMovies.postValue(createViewDataMovies(movies))
            dataSerials.postValue(createViewDataSerials(serials))
        }
    }

    private fun createViewDataMovies(movies: List<Movie>): List<ItemViewModel> {
        val moviesViewData = mutableListOf<ItemViewModel>()
        movies.forEach {
            moviesViewData.add(ItemPopularMovieViewModel(it))
        }
        return moviesViewData
    }

    private fun createViewDataSerials(serials: List<Movie>): List<ItemViewModel> {
        val serialsViewData = mutableListOf<ItemViewModel>()
        serials.forEach {
            serialsViewData.add(ItemPopularMovieViewModel(it))
        }
        return serialsViewData
    }


}