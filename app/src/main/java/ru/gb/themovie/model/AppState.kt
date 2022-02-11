package ru.gb.themovie.model

sealed class AppState {
    data class Success(var dataSet : ArrayList<Movie>, val repo: Repository) : AppState(){
        init {
            dataSet = repo.getMoviesFromLocalStorage()
        }
    }
    class Error() : AppState()
    object Loading : AppState()
}