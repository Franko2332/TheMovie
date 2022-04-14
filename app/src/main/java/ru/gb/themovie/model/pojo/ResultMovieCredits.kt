package ru.gb.themovie.model.pojo

data class ResultMovieCredits(
    val id: Int,
    val cast: ArrayList<MovieCreditsCastModel>
)