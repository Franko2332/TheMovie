package ru.gb.themovie.model

import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.android.parcel.RawValue


@Parcelize
data class Movie(val movieIcon: @RawValue Drawable,
                 val movieName: String,
                 val movieGenr: String,
                 val movieId: Int = 0):Parcelable {
    var rating: Int = 0
    var movieDescription: String = ""
    var movieDuration: String = ""

    constructor(
            _movieIcon: Drawable,
            _movieName: String,
            _movieGenr: String,
            _movieId: Int,
            _rating: Int): this(_movieIcon, _movieName, _movieGenr, _movieId) {
        rating = _rating
    }

    constructor( _movieIcon: Drawable,
                 _movieName: String,
                 _movieGenr: String,
                 _movieId: Int,
                 _rating: Int,
                 _movieDuration: String,
                 _movieDescription: String): this(_movieIcon, _movieName, _movieGenr, _movieId){
        rating = _rating
        movieDescription = _movieDescription
        movieDuration = _movieDuration
    }

    public fun getShortDescription(): String  = movieGenr + "-" +movieDuration

}