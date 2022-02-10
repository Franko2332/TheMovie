package ru.gb.themovie.model

interface Repository {
    fun getMoviesFromServer(): ArrayList<Movie>
    fun getMoviesFromLocalStorage(): ArrayList<Movie>
}