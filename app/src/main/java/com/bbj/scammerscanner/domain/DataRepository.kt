package com.bbj.scammerscanner.domain

import com.bbj.scammerscanner.data.models.CallInfo
import com.bbj.scammerscanner.data.models.NumberType
import com.bbj.scammerscanner.data.models.SMSModel

interface DataRepository {

    suspend fun getCallLogs() : ArrayList<CallInfo>

    suspend fun getSms() : ArrayList<SMSModel>

    suspend fun checkNumber(number : String) : NumberType

}