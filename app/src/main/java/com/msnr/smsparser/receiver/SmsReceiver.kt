package com.msnr.smsparser.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.msnr.smsparser.network.WebService
import com.msnr.smsparser.network.pojo.ResponsePojo
import com.msnr.smsparser.utils.Methods
import com.msnr.smsparser.utils.NotificationHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SmsReceiver : BroadcastReceiver() {

    @SuppressLint("NewApi")
    override fun onReceive(context: Context, intent: Intent) {
        val extras = intent.extras ?: return

        val pdus = extras.get("pdus") as Array<*>
        for (i in pdus.indices) {
            val smsMessage = android.telephony.SmsMessage.createFromPdu(pdus[i] as ByteArray)
            val sender = smsMessage.displayOriginatingAddress
            val messageBody = smsMessage.messageBody

            val urls = Methods.extractUrls(messageBody)
            Log.v("TAG", "onReceive: $urls")

            for (url in urls) {
                var tempUrl=url
                if(!tempUrl.startsWith("https://")){
                    if (!tempUrl.startsWith("http://"))
                        tempUrl="https://$tempUrl"
                }
                WebService.client?.checkUrl(tempUrl, "json")
                    ?.enqueue(object : Callback<ResponsePojo> {
                        override fun onResponse(
                            call: Call<ResponsePojo>,
                            response: Response<ResponsePojo>
                        ) {
                            val responsePojo = response.body()
                            if (responsePojo != null) {
                                if (responsePojo.errortext==""||responsePojo.errortext != null)
                                    return
                                if (!responsePojo.results.verified) {
                                    NotificationHelper.displayNotification(
                                        context,
                                        tempUrl,
                                        "This is a phishing url"
                                    )
                                }
                            }
                        }

                        override fun onFailure(call: Call<ResponsePojo>, t: Throwable) {
                            t.stackTrace
                        }
                    })
            }
        }
    }
}