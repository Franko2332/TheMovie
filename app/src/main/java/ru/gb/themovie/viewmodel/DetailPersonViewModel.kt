package ru.gb.themovie.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gb.themovie.App
import ru.gb.themovie.model.AppState
import ru.gb.themovie.model.pojo.PersonDetailModel
import ru.gb.themovie.model.repository.Repository
import ru.gb.themovie.model.repository.RepositoryImpl

class DetailPersonViewModel() : ViewModel() {
    private var liveData = MutableLiveData<AppState>()
    private var repository: Repository = RepositoryImpl()

    fun getData(_id: Int): MutableLiveData<AppState> {
        loadData(_id)
        return liveData
    }

    private fun loadData(_id: Int) {
        liveData.postValue(AppState.Loading)
        repository.getPerson(_id).enqueue(object : Callback<PersonDetailModel> {
            override fun onResponse(
                call: Call<PersonDetailModel>,
                response: Response<PersonDetailModel>
            ) {
                Log.e("GET_PERSON_BODY", response.raw().request().url().toString())
                when (response.code()) {
                    200 -> {
                        liveData.postValue(
                            AppState.SuccessPersonDetail(response.body() as PersonDetailModel)
                        )
                    }
                }
            }

            override fun onFailure(call: Call<PersonDetailModel>, t: Throwable) {
                Log.e("GET_PERSON_BODY", "ERROR RESPONSE")
                liveData.postValue(AppState.Error)
            }

        })
    }

    fun clearLiveData() {
        liveData.postValue(AppState.Loading)
    }
}