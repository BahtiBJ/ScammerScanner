package com.bbj.scammerscanner.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbj.scammerscanner.data.models.CallInfo
import com.bbj.scammerscanner.data.models.SMSModel
import com.bbj.scammerscanner.domain.GetCallLogsUseCase
import com.bbj.scammerscanner.domain.GetSmsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val getCallLogsUseCase: GetCallLogsUseCase,
    private val getSmsUseCase: GetSmsUseCase
) : ViewModel() {

    private val _liveCallLogs = MutableLiveData<ArrayList<CallInfo>>()
    val liveCallLogs: LiveData<ArrayList<CallInfo>>
        get() = _liveCallLogs

    private val _liveSmsModels = MutableLiveData<ArrayList<SMSModel>>()
    val liveSmsModels: LiveData<ArrayList<SMSModel>>
        get() = _liveSmsModels

    fun claimCallLogs() {
        viewModelScope.launch {
            val callLogs = async {
                getCallLogsUseCase.execute()
            }.await()
            withContext(Dispatchers.Main){
                _liveCallLogs.value = callLogs
            }
        }
    }

    fun claimSms() {
        viewModelScope.launch {
            val smsModels = async {
                getSmsUseCase.execute()
            }.await()
            withContext(Dispatchers.Main){
                _liveSmsModels.value = smsModels
            }
        }
    }


}