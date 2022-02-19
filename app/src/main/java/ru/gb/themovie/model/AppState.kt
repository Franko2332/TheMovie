package ru.gb.themovie.model

sealed class AppState {
    data class Success(var dataSet: ArrayList<Movie> = ArrayList(),
                       var dataSetSerials: ArrayList<Movie> = ArrayList(),
                       val repo: Repository) : AppState(){
        init {
            dataSet = repo.getMoviesFromLocalStorage()
            dataSetSerials = repo.getSerialsFromLocalStorage()
        }
    }
    data class SuccessDetailMovie(var movieId : Int, val repo: Repository) : AppState(){
        var movie: Movie? = null
        init {
            movie = repo.getMovieById(movieId)
        }
    }
    class Error() : AppState()
    object Loading : AppState()
}