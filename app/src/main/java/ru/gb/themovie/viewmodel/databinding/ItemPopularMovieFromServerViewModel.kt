package ru.gb.themovie.viewmodel.databinding

import ru.gb.themovie.R
import ru.gb.themovie.model.ConstForItemsViewModel
import ru.gb.themovie.model.pojo.MovieModel

class ItemPopularMovieFromServerViewModel(override val movie: MovieModel) : ItemViewModel {
    override val layoutId: Int = R.layout.item_popular_movie
    override val viewType: Int = ConstForItemsViewModel.POPULAR_MOVIE_IN_CINEMA
    override val movieId: Int?
        get() = movie.id
    var colorId: Int = R.color.gray
        get() = field

    init {
        multRatingColor()
    }

    private fun multRatingColor() {
        when {
            movie.vote_average!! > 7 -> {
                colorId = R.color.green
            }
            movie.vote_average >= 6 && movie.vote_average < 7 -> {
                colorId = R.color.orange
            }
        }

    }
}