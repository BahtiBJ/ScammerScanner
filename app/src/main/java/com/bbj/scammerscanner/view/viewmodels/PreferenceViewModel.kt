package com.bbj.scammerscanner.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbj.scammerscanner.data.models.NumberType
import com.bbj.scammerscanner.data.room.MaybeScammerNumbers
import com.bbj.scammerscanner.data.room.ScammerNumbers
import com.bbj.scammerscanner.data.room.SuspiciousNumbers
import com.bbj.scammerscanner.domain.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PreferenceViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _liveScammerNumbers = MutableLiveData<List<ScammerNumbers>>()
    val liveScammerNumbers: LiveData<List<ScammerNumbers>>
        get() = _liveScammerNumbers

    private val _liveMaybeScammerNumbers = MutableLiveData<List<MaybeScammerNumbers>>()
    val liveMaybeScammerNumbers: LiveData<List<MaybeScammerNumbers>>
        get() = _liveMaybeScammerNumbers

    private val _liveSuspiciousNumbers = MutableLiveData<List<SuspiciousNumbers>>()
    val liveSuspiciousNumbers: LiveData<List<SuspiciousNumbers>>
        get() = _liveSuspiciousNumbers

    fun deleteFromDB(number : String,numberType: NumberType){
        viewModelScope.launch(Dispatchers.Default) {
            dataRepository.deleteNumber(number,numberType)
        }
    }

    fun claimScammerNumbers() {
        viewModelScope.launch(Dispatchers.Default) {
            val scammerNumbers = withContext(Dispatchers.Default) {
                dataRepository.getScammerNumbers()
            }
            withContext(Dispatchers.Main) {
                _liveScammerNumbers.value = scammerNumbers
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

}