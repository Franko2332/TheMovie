package ru.gb.themovie.model

sealed class AppState {
    data class Success(var dataSetMovies: ArrayList<Movie> = ArrayList(),
                       var dataSetSerials: ArrayList<Movie> = ArrayList(),
                       val repo: Repository) : AppState(){
        init {
            dataSetMovies = repo.getMoviesFromLocalStorage()
            dataSetSerials = repo.getSerialsFromLocalStorage()
        }
    }
    data class SuccessDetailMovie(var movieId : Int, val repo: Repository) : AppState(){
        var movie: Movie? = let { repo.getMovieById(movieId) }
    }
    class Error() : AppState()
    object Loading : AppState()
}