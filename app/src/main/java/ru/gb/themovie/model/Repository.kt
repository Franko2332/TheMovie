package ru.gb.themovie.model

interface Repository {
    fun getMoviesFromServer(): ArrayList<Movie>
    fun getMoviesFromLocalStorage(): ArrayList<Movie>
    fun getSerialsFromLocalStorage(): ArrayList<Movie>
    fun getMovieById(_id: Int):Movie?
}