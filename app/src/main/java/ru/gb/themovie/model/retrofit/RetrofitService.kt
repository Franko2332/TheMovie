package ru.gb.themovie.model.retrofit

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.themovie.model.Const
import java.util.concurrent.TimeUnit


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
