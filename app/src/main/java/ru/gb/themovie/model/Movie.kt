package ru.gb.themovie.model

import android.graphics.drawable.Drawable
import ru.gb.themovie.R
import java.io.Serializable

data class Movie(val movieIcon: Drawable, val movieName: String, val movieGenr: String):Serializable {
}