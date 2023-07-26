package com.msnr.smsparser

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.msnr.smsparser.utils.Consts.Companion.CHANNEL_ID
import com.msnr.smsparser.utils.Consts.Companion.CHANNEL_NAME

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val notifyChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notifyChannel.description = "This is notifier"
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(notifyChannel)
        }
    }
}