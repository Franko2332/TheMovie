package ru.gb.themovie.model.pojo



data class ResultMovieList(
    val page: Int,
    val results: ArrayList<MovieModel>,
    val total_pages: Int,
    val total_results: Int
)