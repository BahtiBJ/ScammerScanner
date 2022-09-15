package com.bbj.scammerscanner.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbj.scammerscanner.data.models.CallInfo
import com.bbj.scammerscanner.data.models.NumberType
import com.bbj.scammerscanner.data.models.SMSModel
import com.bbj.scammerscanner.data.room.MaybeScammerNumbers
import com.bbj.scammerscanner.data.room.ScammerNumbers
import com.bbj.scammerscanner.data.room.SuspiciousNumbers
import com.bbj.scammerscanner.domain.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _liveCallLogs = MutableLiveData<ArrayList<CallInfo>>()
    val liveCallLogs: LiveData<ArrayList<CallInfo>>
        get() = _liveCallLogs

    private val _liveSmsModels = MutableLiveData<ArrayList<SMSModel>>()
    val liveSmsModels: LiveData<ArrayList<SMSModel>>
        get() = _liveSmsModels

    private val _liveScammerNumbers = MutableLiveData<List<ScammerNumbers>>()
    val liveScammerNumbers: LiveData<List<ScammerNumbers>>
        get() = _liveScammerNumbers

    private val _liveMaybeScammerNumbers = MutableLiveData<List<MaybeScammerNumbers>>()
    val liveMaybeScammerNumbers: LiveData<List<MaybeScammerNumbers>>
        get() = _liveMaybeScammerNumbers

    private val _liveSuspiciousNumbers = MutableLiveData<List<SuspiciousNumbers>>()
    val liveSuspiciousNumbers: LiveData<List<SuspiciousNumbers>>
        get() = _liveSuspiciousNumbers

    fun claimCallLogs() {
        viewModelScope.launch(Dispatchers.Default) {
            val callLogs = async {
                dataRepository.getCallLogs()
            }.await()
            withContext(Dispatchers.Main) {
                _liveCallLogs.value = callLogs
            }
        }
    }

    fun claimSms() {
        viewModelScope.launch(Dispatchers.Default) {
            val smsModels = async {
                dataRepository.getSms()
            }.await()
            withContext(Dispatchers.Main) {
                _liveSmsModels.value = smsModels
            }
        }
    }


    fun claimMaybeScammerNumbers() {
        viewModelScope.launch(Dispatchers.Default) {
            val maybeScammerNumbers = withContext(Dispatchers.Default) {
                dataRepository.getMaybeScammerNumbers()
            }
            withContext(Dispatchers.Main) {
                _liveMaybeScammerNumbers.value = maybeScammerNumbers
            }
        }
    }

    fun claimSuspiciousNumbersNumbers() {
        viewModelScope.launch(Dispatchers.Default) {
            val suspiciousNumbers = withContext(Dispatchers.Default) {
                dataRepository.getSuspiciousNumbers()
            }
            withContext(Dispatchers.Main) {
                _liveSuspiciousNumbers.value = suspiciousNumbers
            }
        }
    }

    fun insertIntoScammers(scammerNumbers: ScammerNumbers) {
        viewModelScope.launch(Dispatchers.Default) {
            dataRepository.insertScammerNumbers(scammerNumbers)
        }
    }

    fun insertIntoMaybeScammers(maybeScammerNumbers: MaybeScammerNumbers) {
        viewModelScope.launch(Dispatchers.Default) {
            dataRepository.insertMaybeScammerNumbers(maybeScammerNumbers)
        }
    }

    fun insertIntoSuspicious(suspiciousNumbers: SuspiciousNumbers) {
        viewModelScope.launch(Dispatchers.Default) {
            dataRepository.insertSuspiciousNumbers(suspiciousNumbers)
        }
    }

    fun deleteFromDB(number : String,numberType: NumberType){
        viewModelScope.launch(Dispatchers.Default) {
            dataRepository.deleteNumber(number,numberType)
        }
    }

}