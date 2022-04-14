package ru.gb.themovie.model

import ru.gb.themovie.App
import ru.gb.themovie.model.pojo.*
import ru.gb.themovie.model.repository.Repository
import ru.gb.themovie.viewmodel.databinding.ItemViewModel

sealed class AppState {
    object Success : AppState()
    data class SuccessDetailMovie(var movieDetail : MovieDetailModel) : AppState()
    data class SuccessMovieCredits(var movieCredits: ArrayList<MovieCreditsCastModel>): AppState()
    data class SuccessPersonDetail(var personDetailModel: PersonDetailModel): AppState()
    object Error : AppState()
    object Loading : AppState()
}