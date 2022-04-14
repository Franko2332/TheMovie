package ru.gb.themovie.model.pojo

data class MovieCreditsCastModel(
    val id: Int,
    val name: String,
    val original_name: String,
    val credit_id: String,
    val profile_path: String,
    val character: String
)
