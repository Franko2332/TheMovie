package ru.gb.themovie.viewmodel.databinding

import androidx.annotation.LayoutRes

interface ItemViewModel {
    @get:LayoutRes
    val layoutId: Int
    val viewType: Int
    get() = 0

    val movieId: Int
    get() = 0
}