package ru.gb.themovie.viewmodel.databinding

import androidx.annotation.LayoutRes
import ru.gb.themovie.model.pojo.MovieModel

interface ItemViewModel {
    @get:LayoutRes
    val layoutId: Int
    val viewType: Int
    get() = 0

    val movie: MovieModel

    val movieId: Int?
    get() = 0
}