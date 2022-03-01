package ru.gb.themovie.model.retrofit

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import ru.gb.themovie.model.Const

object RetrofitService {
    private var retrofit: Retrofit? = null

    fun getMovieDBApiService(): MovieDatabaseApi {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(MovieDatabaseApi::class.java)
    }
}
