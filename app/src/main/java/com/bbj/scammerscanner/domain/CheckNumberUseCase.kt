package com.bbj.scammerscanner.domain

import com.bbj.scammerscanner.data.models.NumberType

class CheckNumberUseCase(private val dataRepository: DataRepository) {

    suspend fun execute(number : String) : NumberType{
        return dataRepository.checkNumber(number)
    }

}