package ru.gb.themovie.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gb.themovie.model.AppState
import ru.gb.themovie.model.Repository
import ru.gb.themovie.model.RepositoryImpl

class MainViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData()) : ViewModel() {
    private lateinit var repo: Repository

    public fun initRepo(context: Context) {
        repo = RepositoryImpl(context)
    }

    public fun getData(): MutableLiveData<AppState> {
        loadMovies()
        return liveData
    }

    private fun loadMovies() {
//        Log.i("VM", "from view model")
//        var random :  Random = Random
//        val randomInt = random.nextInt(2)
//        if (randomInt == 1) {
            liveData.postValue(AppState.Success(ArrayList(), ArrayList(), repo))
//        } else if (randomInt == 0) {
//            liveData.postValue(AppState.Error())
//        }
    }


}