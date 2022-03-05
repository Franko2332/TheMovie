package ru.gb.themovie.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import java.lang.StringBuilder

class MainBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        StringBuilder().apply { append ("System Message")
        append("Action: ")
        append("${intent?.action}").toString().also {
            Log.e("intent", it)
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }}

    }
}