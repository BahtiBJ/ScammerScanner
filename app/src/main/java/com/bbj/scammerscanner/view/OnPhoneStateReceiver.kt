package com.bbj.scammerscanner.view

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class OnPhoneStateReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null)
            Toast.makeText(context, "$context.", Toast.LENGTH_LONG).show()
        notify(context!!,"FROM BROADCAST RECIEVRE")
    }

    fun notify(context: Context, text: String) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(NotificationChannel("0","def",NotificationManager.IMPORTANCE_HIGH))
        val builder = NotificationCompat.Builder(context, "0")
            .setSmallIcon(android.R.drawable.ic_dialog_email)
            .setContentTitle("Напоминание")
            .setTicker("TICKER")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentText(text)
        manager.notify(0, builder.build())
    }
}