package ru.gb.themovie.model

import ru.gb.themovie.model.pojo.MovieDetailModel
import ru.gb.themovie.model.pojo.MovieModel
import ru.gb.themovie.model.repository.Repository
import ru.gb.themovie.viewmodel.databinding.ItemViewModel

sealed class AppState {
    object Success : AppState()
    data class SuccessDetailMovie(var movieDetail : MovieDetailModel) : AppState()
    object Error : AppState()
    object Loading : AppState()
}