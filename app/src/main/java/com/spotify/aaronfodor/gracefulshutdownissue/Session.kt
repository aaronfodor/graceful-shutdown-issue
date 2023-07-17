package com.spotify.aaronfodor.gracefulshutdownissue

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log

class Session(private val context: Context?) {

    fun start() {
        Log.d(TAG, "Session started")
    }

    fun stop() {
        Log.d(TAG, "Session is stopping...")
        performBackgroundTask()
    }

    private fun performBackgroundTask() = Thread {
        Log.d(TAG, "Shutting down on a background thread...")
        Thread.sleep(100)
        Handler(Looper.getMainLooper()).post {
            Log.d(TAG, "Session stopped ✅")
            val serviceIntent = Intent(context, SomeService::class.java)
            context?.stopService(serviceIntent)
        }
    }.start()

    private fun otherBackgroundTask() = Thread {
        while(true){
            Log.e(TAG, "shutting down...")
        }
    }.start()

    companion object {
        const val TAG = "Session"
    }

}