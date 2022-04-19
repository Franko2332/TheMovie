package ru.gb.themovie

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFireBaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val PUSH_KEY_TITLE = "title"
        private const val PUSH_KEY_MESSAGE = "text"
        private const val CHANNEL_ID = "chanel_id"
        private const val NOTIFICATION_ID = 37

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val remoteMessageData = remoteMessage.data
        if (remoteMessageData.isNotEmpty()) {
            handleDataMessage(remoteMessage.data.toMutableMap())
        }
    }

    private fun handleDataMessage(data: Map<String, String>) {
        val text = data[PUSH_KEY_MESSAGE]
        val title = data[PUSH_KEY_TITLE]
        if (!title.isNullOrBlank() && !text.isNullOrBlank()) {
            showNotification(title, text)
        }
    }

    private fun showNotification(title: String, message: String) {
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_person)
            setContentTitle(title)
            setContentText(message)
            priority = NotificationCompat.PRIORITY_HIGH
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChanel(notificationManager)
        }
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChanel(notificationManager: NotificationManager) {
        val name = "channel_name"
        val descriptionText = "chamnel_description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val chanel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        notificationManager.createNotificationChannel(chanel)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}