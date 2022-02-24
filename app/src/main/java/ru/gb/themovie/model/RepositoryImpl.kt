package ru.gb.themovie.model

import android.content.Context
import ru.gb.themovie.R
import kotlin.random.Random

class RepositoryImpl(private val context : Context):Repository {

    var dataMovies = ArrayList<Movie>().apply {
        add(Movie(context.resources.getDrawable(ru.gb.themovie.R.drawable.movie1),
                "355", "Драмма", 1, 70, "2:21",
                context.getString(ru.gb.themovie.R.string.movie_description_1)))
        add(Movie(context.resources.getDrawable(R.drawable.movie2),
                "Падение луны", "Драмма", 2, 85, "1:21",
                context.getString(R.string.movie_description_2)))
        add(Movie(context.resources.getDrawable(R.drawable.movie3),
                "Kings Man", "Триллер", 3, 61, "2:03",
                context.getString(R.string.movie_description_3)))
        add(Movie(context.resources.getDrawable(R.drawable.movie4),
                "Анчартед", "Драмма", 4, 78, "1:40",
                context.getString(R.string.movie_description_4)))
        add(Movie(context.resources.getDrawable(R.drawable.movie5),
                "Аллея Кошмаров", "Триллер", 5, 89, "1:55",
                context.getString(R.string.movie_description_5)))
        add(Movie(context.resources.getDrawable(R.drawable.movie6),
                "Мы монстры", "Мультфильм", 6, 73, "2:40",
                context.getString(R.string.movie_description_6)))
        add(Movie(context.resources.getDrawable(R.drawable.movie7),
                "Дочь короля", "Драмма", 7, 68, "2:21",
                context.getString(R.string.movie_description_7)))
        add(Movie(context.resources.getDrawable(R.drawable.movie8),
                "Чудаки навсегда", "Комедия", 8, 86, "2:30",
                context.getString(R.string.movie_description_8)))
        add(Movie(context.resources.getDrawable(R.drawable.movie9),
                "Первый встречный", "Комедия", 9, 92, "2:05",
                context.getString(R.string.movie_description_9)))
        add(Movie(context.resources.getDrawable(R.drawable.movie10, ),
                "Мой волк", "Драмма", 10, 77, "2:11",
                context.getString(R.string.movie_description_10))) }

    var dataSerials = ArrayList<Movie>().apply {
        add(ru.gb.themovie.model.Movie(context.resources.getDrawable(ru.gb.themovie.R.drawable.serial1),
                "Мы все мертвы", "Ужасы", 11, 70, "50",
                context.getString(ru.gb.themovie.R.string.serial_description_1)))
        add(Movie(context.resources.getDrawable(R.drawable.serial2),
                "Миротворец", "Драмма", 12, 85, "50",
                context.getString(R.string.serial_description_2)))
        add(Movie(context.resources.getDrawable(R.drawable.serial3),
                "Passion", "Триллер", 13, 61, "50",
                context.getString(R.string.serial_description_3)))
        add(Movie(context.resources.getDrawable(R.drawable.serial4),
                "Звездные войны", "Боевик", 14, 78, "50",
                context.getString(R.string.serial_description_4)))
        add(Movie(context.resources.getDrawable(R.drawable.serial5),
                "Ходячие мертвецы", "Триллер", 15, 89, "50",
                context.getString(R.string.serial_description_5)))
        add(Movie(context.resources.getDrawable(R.drawable.serial6),
                "Секретная история", "Мультфильм", 16, 73, "50",
                context.getString(R.string.serial_description_6)))
    }

    var dataAllMovies = ArrayList<Movie>().apply {
        for(i in 0 until dataMovies.size){
            add(dataMovies[i])
        }
        for(i in 0 until dataSerials.size){
            add(dataSerials[i])
        }
    }

    override fun getMoviesFromServer(): ArrayList<Movie> {
        for (i in 1..dataMovies.size){
            dataMovies.removeAt(i)
        }
        for (i in 1..10){
            dataMovies.add(Movie(context.resources.getDrawable(R.drawable.ic_done),
                    "Troy", "Action", Random.nextInt(60, 30)))
        }
        return dataMovies
    }

    override fun getMoviesFromLocalStorage(): ArrayList<Movie> = dataMovies

    override fun getSerialsFromLocalStorage(): ArrayList<Movie> = dataSerials

    fun getAllMovies(): ArrayList<Movie> = dataAllMovies

    override fun getMovieById(_id: Int): Movie? {
        for (i in 0 until dataAllMovies.size){
            if (dataAllMovies[i].movieId==_id) return dataAllMovies[i]
        }
        return null
    }
}