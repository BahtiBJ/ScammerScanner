package com.bbj.scammerscanner.domain

import com.bbj.scammerscanner.data.models.SMSModel

class GetSmsUseCase(private val dataRepository: DataRepository) {

    suspend fun execute() : ArrayList<SMSModel>{
        return dataRepository.getSms()
    }

}