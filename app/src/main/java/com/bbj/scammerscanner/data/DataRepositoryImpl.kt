package com.bbj.scammerscanner.data

import com.bbj.scammerscanner.data.models.CallInfo
import com.bbj.scammerscanner.data.models.NumberType
import com.bbj.scammerscanner.data.models.SMSModel
import com.bbj.scammerscanner.domain.DataRepository

class DataRepositoryImpl : DataRepository{

    override suspend fun getCallLogs(): ArrayList<CallInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun getSms(): ArrayList<SMSModel> {
        TODO("Not yet implemented")
    }

    override suspend fun checkNumber(number: String): NumberType {
        TODO("Not yet implemented")
    }
}