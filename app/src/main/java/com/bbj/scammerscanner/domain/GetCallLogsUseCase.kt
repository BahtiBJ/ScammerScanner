package com.bbj.scammerscanner.domain

import com.bbj.scammerscanner.data.models.CallInfo

class GetCallLogsUseCase(private val dataRepository: DataRepository) {

    suspend fun execute() : ArrayList<CallInfo>{
        return dataRepository.getCallLogs()
    }

}