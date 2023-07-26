package com.msnr.smsparser.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.msnr.smsparser.utils.NotificationHelper
import java.util.Objects

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val extras = intent.extras ?: return

        val pdus = extras.get("pdus") as Array<*>
        for (i in pdus.indices) {
            val smsMessage = android.telephony.SmsMessage.createFromPdu(pdus[i] as ByteArray)
            val sender = smsMessage.displayOriginatingAddress
            val messageBody = smsMessage.messageBody

            NotificationHelper.displayNotification(context, sender, messageBody)
        }

    }
}