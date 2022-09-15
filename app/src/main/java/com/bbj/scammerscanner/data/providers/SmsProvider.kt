package com.bbj.scammerscanner.data.providers

import android.content.Context
import android.provider.CallLog
import android.provider.Telephony
import com.bbj.scammerscanner.data.models.SMSModel

class SmsProvider(context : Context) {

    private val contentResolver = context.contentResolver
    private val smsCountLimit = 30

    fun provideSms(): ArrayList<SMSModel> {
        val addressCol = Telephony.Sms.ADDRESS
        val bodyConst = Telephony.Sms.BODY
        val typeConst = Telephony.Sms.TYPE

        val resultArray: ArrayList<SMSModel> = arrayListOf()
        val smsCursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf(addressCol, bodyConst, typeConst),
            null,
            null,
            CallLog.Calls.DEFAULT_SORT_ORDER,
            null
        )
        val addressId = smsCursor!!.getColumnIndex(addressCol)
        val bodyId = smsCursor.getColumnIndex(bodyConst)
        val typeId = smsCursor.getColumnIndex(typeConst)

        var callCount = 0
        while (smsCursor.moveToNext() && callCount < smsCountLimit) {
            val body = smsCursor.getString(bodyId)
            val address = smsCursor.getString(addressId)
            val type = smsCursor.getString(typeId)
            resultArray.add(SMSModel(address,body,type))
            callCount++
        }

        smsCursor.close()
        return resultArray
    }

}