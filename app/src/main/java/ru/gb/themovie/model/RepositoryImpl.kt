package ru.gb.themovie.model

import android.content.Context
import ru.gb.themovie.R
import kotlin.random.Random

class RepositoryImpl(private val context : Context):Repository {
    val data: ArrayList<Movie> = ArrayList()
    init {
        data.add(Movie(context.resources.getDrawable(R.drawable.movie1),
            "355", "Драмма", 1,70, "2:21",
            context.getString(R.string.movie_description_1)))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie2),
            "Падение луны", "Драмма", 2, 85, "1:21",
            context.getString(R.string.movie_description_2)))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie3),
            "Kings Man", "Триллер", 3, 61, "2:03",
            context.getString(R.string.movie_description_3)))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie4),
            "Анчартед", "Драмма", 4,78, "1:40",
            context.getString(R.string.movie_description_4)))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie5),
            "Аллея Кошмаров", "Триллер", 5,89, "1:55",
            context.getString(R.string.movie_description_5)))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie6),
            "Мы монстры", "Мультфильм", 6, 73, "2:40",
            context.getString(R.string.movie_description_6)))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie7),
            "Дочь короля", "Драмма", 7, 68, "2:21",
            context.getString(R.string.movie_description_7)))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie8),
            "Чудаки навсегда", "Комедия", 8,86, "2:30",
            context.getString(R.string.movie_description_8)))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie9),
            "Первый встречный", "Комедия", 9,92, "2:05",
            context.getString(R.string.movie_description_9)))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie10, ),
            "Мой волк", "Драмма", 10,77, "2:11",
            context.getString(R.string.movie_description_10)))
    }
    override fun getMoviesFromServer(): ArrayList<Movie> {
        for (i in 1..10){
            data.add(Movie(context.resources.getDrawable(R.drawable.ic_done),
                    "Troy", "Action", Random.nextInt(60, 30)))
        }
        return data
    }

    override fun getMoviesFromLocalStorage(): ArrayList<Movie> {
        return data
    }

    override fun getMovieById(_id: Int): Movie? {
        for (i in 0 .. data.size){
            if (data[i].movieId==_id) return data[i]
        }
        return null
    }
}