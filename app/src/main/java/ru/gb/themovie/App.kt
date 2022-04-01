package ru.gb.themovie

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.room.Room
import ru.gb.themovie.model.room.MovieDataBase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var movieDataBase: MovieDataBase? = null
        private const val DB_NAME = "TheMovieDatabase.db"

        fun getMovieDatabase(): MovieDataBase {
            if (movieDataBase == null) {
                var runnable = Runnable { if (movieDataBase == null) {
                    if (appInstance == null) throw
                    IllegalStateException("Application is null while creating DataBase")
                    movieDataBase = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        MovieDataBase::class.java, DB_NAME
                    )
                        .allowMainThreadQueries()
                        .build()
                } }
                val thread = Thread(runnable)
                thread.join()
                thread.run()
            }
            return movieDataBase!!
        }
    }

}