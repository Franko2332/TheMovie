package ru.gb.themovie.model

import android.content.Context
import ru.gb.themovie.R

class RepositoryImpl(private val context : Context):Repository {
    override fun getMoviesFromServer(): ArrayList<Movie> {
        val data : ArrayList<Movie> = ArrayList()
        for (i in 1..10){
            data.add(Movie(context.resources.getDrawable(R.drawable.ic_done),
                    "Troy", "Action"))
        }
        return data
    }

    override fun getMoviesFromLocalStorage(): ArrayList<Movie> {
        val data : ArrayList<Movie> = ArrayList()
            data.add(Movie(context.resources.getDrawable(R.drawable.movie1),
                    "", ""))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie2),
                "", ""))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie3),
                "", ""))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie4),
                "", ""))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie5),
                "", ""))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie6),
                "", ""))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie7),
                "", ""))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie8),
                "", ""))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie9),
                "", ""))
        data.add(Movie(context.resources.getDrawable(R.drawable.movie10),
                "", ""))
        return data
    }
}