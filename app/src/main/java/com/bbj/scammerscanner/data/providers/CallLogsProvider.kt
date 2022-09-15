package com.bbj.scammerscanner.data.providers

import android.content.Context
import android.provider.CallLog
import com.bbj.scammerscanner.data.models.CallInfo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CallLogsProvider(context : Context) {

    private val contentResolver = context.contentResolver
    private val logsCountLimit = 30

    fun provideCallLogs() : ArrayList<CallInfo>{
        val numberConst = CallLog.Calls.NUMBER
        val dateConst = CallLog.Calls.DATE
        val typeConst = CallLog.Calls.TYPE

        val resultArray: ArrayList<CallInfo> = arrayListOf()
        val callCursor = contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            arrayOf(numberConst, dateConst, typeConst),
            null,
            null,
            CallLog.Calls.DEFAULT_SORT_ORDER,
            null
        )
        val numberId = callCursor!!.getColumnIndex(numberConst)
        val dateId = callCursor.getColumnIndex(dateConst)
        val typeId = callCursor.getColumnIndex(typeConst)

        var callCount = 0
        while (callCursor.moveToNext() && callCount < logsCountLimit) {
            val date = getDateTime(callCursor.getString(dateId)) ?: ""
            val number = callCursor.getString(numberId)
            val type = callCursor.getInt(typeId)
            resultArray.add(CallInfo(number, date,type))
            callCount++
        }

        callCursor.close()
        return resultArray
    }

    private fun getDateTime(s: String): String? {
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val netDate = Date((s.toLong()))
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

}