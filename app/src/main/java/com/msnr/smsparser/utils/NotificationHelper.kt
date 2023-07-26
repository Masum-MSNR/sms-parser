package com.msnr.smsparser.utils

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.msnr.smsparser.utils.Consts.Companion.CHANNEL_ID
import com.msnr.smsparser.activities.MainActivity
import com.msnr.smsparser.R

@SuppressLint("MissingPermission")
class NotificationHelper {
    companion object {
        fun displayNotification(context: Context, title: String, body: String) {
            val resultIntent = Intent(context, MainActivity::class.java)
            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(
                    context,
                    1,
                    resultIntent,
                    PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            val notificationManagerCompat: NotificationManagerCompat =
                NotificationManagerCompat.from(context)
            val notification: Notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build()
            notificationManagerCompat.notify(1, notification)
        }
    }
}