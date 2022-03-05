package ru.gb.themovie.model.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


data class ResultMovieList(
    val page: Int,
    val results: ArrayList<MovieModel>,
    val total_pages: Int,
    val total_results: Int
)  : Serializable