package ru.gb.themovie.view

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.gb.themovie.model.pojo.ResultMovieList
import ru.gb.themovie.model.repository.Repository
import ru.gb.themovie.model.repository.RepositoryImpl

const val MAIN_SERVICE_INT_EXTRA = "MainServiceIntExtra"
class Service(name: String = "MainService") : IntentService(name) {

    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            sendBack( it.getIntExtra(MAIN_SERVICE_INT_EXTRA, 0))
        }
    }

    private fun sendBack(intExtra: Int) {
        when(intExtra){
            1 -> {
                loadData()
        }
        }
    }

    private fun loadData() {
       val repo: Repository = RepositoryImpl(baseContext)
        val response = repo.getMoviesFromServer().execute()

        when (response.code()){
            200 ->{
                val result =  response.body() as ResultMovieList
                val broadcastIntent = Intent(TEST_BROADCAST_INTENT_FILTER)
                    .apply { putExtra(MOVIES_DATA_BROADCAST_EXTRA, result)
                    LocalBroadcastManager.getInstance(baseContext).sendBroadcast(this) }

            }
        }


    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}