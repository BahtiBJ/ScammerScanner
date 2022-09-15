package com.bbj.scammerscanner.data.providers

import android.content.Context
import android.provider.CallLog
import com.bbj.scammerscanner.data.models.CallInfo

class CollLogsProvider(context : Context) {

    private val contentResolver = context.contentResolver
    private val logsCountLimit = 30

    fun provideCallLogs() : ArrayList<CallInfo>{
        val numberConst = CallLog.Calls.NUMBER
        val durationConst = CallLog.Calls.DURATION
        val typeConst = CallLog.Calls.TYPE

        val resultArray: ArrayList<CallInfo> = arrayListOf()
        val callCursor = contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            arrayOf(numberConst, durationConst, typeConst),
            null,
            null,
            CallLog.Calls.DEFAULT_SORT_ORDER,
            null
        )
        val numberId = callCursor!!.getColumnIndex(numberConst)
        val durationId = callCursor.getColumnIndex(durationConst)
        val typeId = callCursor.getColumnIndex(typeConst)

        var callCount = 0
        while (callCursor.moveToNext() && callCount < logsCountLimit) {
            val duration = callCursor.getString(durationId)
            val number = callCursor.getString(numberId)
            resultArray.add(CallInfo(number, duration))
            callCount++
        }

        callCursor.close()
        return resultArray
    }

}