package com.spotify.aaronfodor.gracefulshutdownissue

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class SomeService : Service() {

    private val session = Session(this)

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        session.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")

        intent?.action?.let { action ->
            if(action == CALL_START_FOREGROUND){
                Log.d(TAG, "startForeground called")
                createNotificationChannel()
                val notification = buildNotification("service is in foreground")
                val notificationIndex = notificationCounter
                this.startForeground(notificationIndex, notification)
            }
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.d(TAG, "service onTaskRemoved")
        session.stop()
        super.onTaskRemoved(rootIntent)
    }

    private fun createNotificationChannel(){
        val notificationChannel = NotificationChannel(
            TAG, "Channel 1", NotificationManager.IMPORTANCE_HIGH
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(notificationChannel)
    }

    private fun buildNotification(text: String) : Notification {
        val builder = NotificationCompat.Builder(this, TAG)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(text)
            .setContentText("")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        return builder.build()
    }

    private var notificationCounter = 1
        get() {
            field += 1
            return field
        }

    companion object{
        const val CALL_START_FOREGROUND = "CALL_START_FOREGROUND"
        const val TAG = "SomeService"
    }
}