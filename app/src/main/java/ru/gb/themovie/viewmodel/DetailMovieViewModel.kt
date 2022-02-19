package ru.gb.themovie.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.themovie.model.AppState
import ru.gb.themovie.model.Movie
import ru.gb.themovie.model.Repository
import ru.gb.themovie.model.RepositoryImpl

class DetailMovieViewModel (private val liveData: MutableLiveData<AppState> = MutableLiveData()) : ViewModel() {
    private lateinit var repo: Repository

    public fun initRepo(context: Context) {
        repo = RepositoryImpl(context)
    }

    public fun getMovie(_id: Int): MutableLiveData<AppState> {
        loadMovies(_id)
        return liveData
    }

    private fun loadMovies(_id: Int) {
//        Log.i("VM", "from view model")
//        var random :  Random = Random
//        val randomInt = random.nextInt(2)
//        if (randomInt == 1) {
        liveData.postValue(AppState.SuccessDetailMovie(_id, repo))
//        } else if (randomInt == 0) {
//            liveData.postValue(AppState.Error())
//        }
    }
}