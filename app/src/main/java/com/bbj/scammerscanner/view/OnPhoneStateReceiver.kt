package com.bbj.scammerscanner.view

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.room.Room
import com.bbj.scammerscanner.R
import com.bbj.scammerscanner.data.PreferenceManager
import com.bbj.scammerscanner.data.models.NumberType
import com.bbj.scammerscanner.data.room.NumbersDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class OnPhoneStateReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context!= null && PreferenceManager(context).getNotificationEnabledState()) {
            val phoneNumber = intent!!.extras?.getString("incoming_number") ?: return
            CoroutineScope(Dispatchers.Default).launch {
                val type = checkType(phoneNumber,context)

                if (type != "") {
                    withContext(Dispatchers.Main) {
                        val message = when (NumberType.valueOf(type)){
                            NumberType.SCAMMER -> {
                                context.resources.getString(R.string.scammer_message)
                            }
                            NumberType.MAYBE_SCAMMER -> {
                                context.resources.getString(R.string.maybe_scammer_message)
                            }
                            NumberType.SUSPICIOUS -> {
                                context.resources.getString(R.string.suspicious_message)
                            }
                            else -> ""
                        }
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        notify(context, message)
                    }
                }
            }
        }
    }

    fun notify(context: Context, text: String) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(NotificationChannel("0","def",NotificationManager.IMPORTANCE_HIGH))
        val builder = NotificationCompat.Builder(context, "0")
            .setSmallIcon(android.R.drawable.ic_dialog_email)
            .setContentTitle("Внимание")
            .setAutoCancel(false)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentText(text)
        manager.notify(0, builder.build())
    }

    private fun checkType(number: String,context : Context): String {
        val dao =
            Room.databaseBuilder(context, NumbersDatabase::class.java, "numbersDatabase")
                .build().getDao()
        val maybeScammerNumber = dao.findInMaybeScammers(number)
        if (maybeScammerNumber.isNotEmpty())
            return NumberType.MAYBE_SCAMMER.toString()
        val suspiciousNumber = dao.findInSuspicious(number)
        if (suspiciousNumber.isNotEmpty())
            return NumberType.SUSPICIOUS.toString()
        val scammerNumber = dao.findInScammer(number)
        if (scammerNumber.isNotEmpty())
            return NumberType.SCAMMER.toString()
        return ""
    }
}