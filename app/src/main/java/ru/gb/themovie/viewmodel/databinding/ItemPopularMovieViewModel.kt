package ru.gb.themovie.viewmodel.databinding

import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.DisplayContext
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import ru.gb.themovie.R
import ru.gb.themovie.model.ConstForItemsViewModel
import ru.gb.themovie.model.Movie

class ItemPopularMovieViewModel(val movie: Movie) : ItemViewModel {
     var colorId: Int = R.color.gray
        get() = field
    override val layoutId: Int = R.layout.item_popular_movie
    override val viewType: Int = ConstForItemsViewModel.POPULAR_MOVIE_IN_CINEMA
    override val movieId: Int
        get() = movie.movieId

    init {
        multRatingColor()
    }

    private fun multRatingColor() {
        when {
            movie.rating > 70 -> {
                colorId = R.color.green
            }
            movie.rating in 60..70 -> {
                colorId = R.color.orange
            }
        }

    }
}