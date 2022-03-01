package ru.gb.themovie.model

import ru.gb.themovie.model.pojo.MovieDetailModel
import ru.gb.themovie.model.pojo.MovieModel
import ru.gb.themovie.model.repository.Repository

sealed class AppState {
    data class Success(
        val repo: Repository) : AppState()
    data class SuccessDetailMovie(var movieDetail : MovieDetailModel) : AppState()
    class Error() : AppState()
    object Loading : AppState()
}