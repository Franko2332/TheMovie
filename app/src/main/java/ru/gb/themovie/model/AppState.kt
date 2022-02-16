package ru.gb.themovie.model

sealed class AppState {
    data class Success(var dataSet : ArrayList<Movie>, val repo: Repository) : AppState(){
        init {
            dataSet = repo.getMoviesFromLocalStorage()
        }
    }
    data class SuccessDetailMovie(var movieId : Int, val repo: Repository) : AppState(){
        var movie: Movie? = null
        init {
            repo.getMoviesFromLocalStorage()
            movie = repo.getMovieById(movieId)
        }
    }
    class Error() : AppState()
    object Loading : AppState()
}