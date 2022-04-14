package ru.gb.themovie.model.pojo

data class PersonDetailModel(
    val birthday: String,
    val id: Int,
    val name: String,
    val biography: String,
    val place_of_birth: String?,
    val profile_path: String,)