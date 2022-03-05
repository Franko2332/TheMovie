package ru.gb.themovie.view

import android.app.IntentService
import android.content.Intent

class Service(name: String?) : IntentService(name) {
    override fun onHandleIntent(p0: Intent?) {

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

    private fun createMessage(message: String){

    }
}