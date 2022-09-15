package com.bbj.scammerscanner.data

import com.bbj.scammerscanner.data.models.CallInfo
import com.bbj.scammerscanner.data.models.NumberType
import com.bbj.scammerscanner.data.models.SMSModel
import com.bbj.scammerscanner.data.providers.CallLogsProvider
import com.bbj.scammerscanner.data.providers.SmsProvider
import com.bbj.scammerscanner.data.room.MaybeScammerNumbers
import com.bbj.scammerscanner.data.room.NumbersDAO
import com.bbj.scammerscanner.data.room.ScammerNumbers
import com.bbj.scammerscanner.data.room.SuspiciousNumbers
import com.bbj.scammerscanner.domain.DataRepository

class DataRepositoryImpl(
    private val callLogsProvider: CallLogsProvider,
    private val smsProvider: SmsProvider,
    private val numbersDAO: NumbersDAO
) : DataRepository {

    override suspend fun getCallLogs(): ArrayList<CallInfo> {
        return callLogsProvider.provideCallLogs()
    }

    override suspend fun getSms(): ArrayList<SMSModel> {
        return smsProvider.provideSms()
    }

    override suspend fun getScammerNumbers(): List<ScammerNumbers> {
        return numbersDAO.getAllFromScammers()
    }

    override suspend fun getMaybeScammerNumbers(): List<MaybeScammerNumbers> {
        return numbersDAO.getAllFromMaybeScammers()
    }

    override suspend fun getSuspiciousNumbers(): List<SuspiciousNumbers> {
        return numbersDAO.getAllFromSuspicious()
    }

    override suspend fun deleteNumber(number: String, numberType: NumberType) {
        when (numberType){
            NumberType.SCAMMER -> {numbersDAO.deleteFromScammers(number)}
            NumberType.MAYBE_SCAMMER -> {numbersDAO.deleteFromMaybeScammers(number)}
            NumberType.SUSPICIOUS -> {numbersDAO.deleteFromSuspicious(number)}
            else -> {}
        }
    }

    override suspend fun insertScammerNumbers(scammerNumbers: ScammerNumbers) {
        numbersDAO.insertInScammers(scammerNumbers)
    }

    override suspend fun insertMaybeScammerNumbers(maybeScammerNumbers: MaybeScammerNumbers) {
        numbersDAO.insertInMaybeScammers(maybeScammerNumbers)
    }

    override suspend fun insertSuspiciousNumbers(suspiciousNumbers: SuspiciousNumbers) {
        numbersDAO.insertInSuspicious(suspiciousNumbers)
    }
}