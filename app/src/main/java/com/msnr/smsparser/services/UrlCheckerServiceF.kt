package com.msnr.smsparser.services

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.msnr.smsparser.R
import com.msnr.smsparser.network.WebService
import com.msnr.smsparser.network.pojo.ResponsePojo
import com.msnr.smsparser.utils.Consts.Companion.CHANNEL_ID
import com.msnr.smsparser.utils.Consts.Companion.URL
import com.msnr.smsparser.utils.NotificationHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UrlCheckerServiceF : Service() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val notification: Notification =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText("Checking Url")
                .build()
        startForeground(69, notification)
        val url: String? = intent.getStringExtra(URL)
        Log.v("TAG", "onStartCommand: $url")

        WebService.client?.checkUrl("https://checkurl.phishtank.com/checkurl/", "json")
            ?.enqueue(object : Callback<ResponsePojo> {
                override fun onResponse(
                    call: Call<ResponsePojo>,
                    response: Response<ResponsePojo>
                ) {
                    val responsePojo = response.body()
                    if(responsePojo!=null){
                        if(responsePojo.results.inDatabase){
                            NotificationHelper.displayNotification(
                                this@UrlCheckerServiceF,
                                "Phishing Url",
                                "This is a phishing url"
                            )
                        }
                    }
                    stopSelf()
                }

                override fun onFailure(call: Call<ResponsePojo>, t: Throwable) {
                    stopSelf()
                    t.stackTrace
                }
            })

        return START_REDELIVER_INTENT
    }


    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}