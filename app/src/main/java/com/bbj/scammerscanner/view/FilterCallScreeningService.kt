package com.bbj.scammerscanner.view

import android.telecom.Call
import android.telecom.CallScreeningService
import androidx.room.Room
import com.bbj.scammerscanner.data.PreferenceManager
import com.bbj.scammerscanner.data.room.NumbersDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilterCallScreeningService : CallScreeningService() {


    override fun onScreenCall(details: Call.Details) {
        CoroutineScope(Dispatchers.Default).launch {
            val response = if (!PreferenceManager(applicationContext).getScammerEnabledState()) {
                CallResponse.Builder().build()
            } else {
                withContext(Dispatchers.Default) {
                    handleCall(getNumber(details))
                }
            }

            withContext(Dispatchers.Main) {
                respondToCall(details, response)
            }
        }
    }

    private fun handleCall(number: String): CallResponse {
        val resultResponse = CallResponse.Builder()

        if (isScammer(number))
            resultResponse.apply {
                setDisallowCall(true)
                setRejectCall(true)
                setSkipCallLog(true)
                setSkipNotification(true)
            }

        return resultResponse.build()
    }

    private fun getNumber(details: Call.Details): String {
        return details.handle.toString().split(":")[1]
    }

    private fun isScammer(number: String): Boolean {
        val dao =
            Room.databaseBuilder(applicationContext, NumbersDatabase::class.java, "numbersDatabase")
                .build().getDao()
        val resultList = dao.findInScammer(number)
        return resultList.isNotEmpty()
    }
}