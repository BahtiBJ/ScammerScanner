package com.bbj.scammerscanner.domain

import com.bbj.scammerscanner.data.models.CallInfo
import com.bbj.scammerscanner.data.models.NumberType
import com.bbj.scammerscanner.data.models.SMSModel
import com.bbj.scammerscanner.data.room.MaybeScammerNumbers
import com.bbj.scammerscanner.data.room.ScammerNumbers
import com.bbj.scammerscanner.data.room.SuspiciousNumbers

interface DataRepository {

    suspend fun getCallLogs() : ArrayList<CallInfo>

    suspend fun getSms() : ArrayList<SMSModel>

    suspend fun getScammerNumbers() : List<ScammerNumbers>

    suspend fun getMaybeScammerNumbers() : List<MaybeScammerNumbers>

    suspend fun getSuspiciousNumbers() : List<SuspiciousNumbers>

    suspend fun deleteNumber(number : String,numberType: NumberType)

    suspend fun insertScammerNumbers(scammerNumbers: ScammerNumbers)

    suspend fun insertMaybeScammerNumbers(maybeScammerNumbers: MaybeScammerNumbers)

    suspend fun insertSuspiciousNumbers(suspiciousNumbers: SuspiciousNumbers)

}