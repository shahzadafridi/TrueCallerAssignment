package com.example.shahzad_afridi.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shahzad_afridi.data.repository.BlogRepository
import com.example.shahzad_afridi.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: BlogRepository
): ViewModel() {

    private val _error = MutableLiveData<Pair<String,String?>>()
    val error: LiveData<Pair<String,String?>>
        get() = _error

    private val _truecaller10thCharacter = MutableLiveData<UiState<Char>>()
    val truecaller10thCharacter: LiveData<UiState<Char>>
        get() = _truecaller10thCharacter

    private val _truecallerEvery10thCharacter = MutableLiveData<UiState<String>>()
    val truecallerEvery10thCharacter: LiveData<UiState<String>>
        get() = _truecallerEvery10thCharacter

    private val _truecallerWordCounter = MutableLiveData<UiState<String>>()
    val truecallerWordCounter: LiveData<UiState<String>>
        get() = _truecallerWordCounter

    /**
     * The app should define and run 3 requests SIMULTANEOUSLY
     */
    fun onGetBlogContent() {
        viewModelScope.launch {
            _truecaller10thCharacter.value = UiState.Loading
            repository.getBlogContent { state ->
                when(state) {
                    is UiState.Success -> {
                        truecaller10thCharacterRequest(state.data.content)
                    }
                    is UiState.Failure -> {
                        _error.postValue(Pair("Truecaller10thCharacter",state.error))
                    }
                }
            }
        }
        viewModelScope.launch {
            _truecallerEvery10thCharacter.value = UiState.Loading
            repository.getBlogContent { state ->
                when(state) {
                    is UiState.Success -> {
                        truecallerEvery10thCharacterRequest(state.data.content)
                    }
                    is UiState.Failure -> {
                        _error.postValue(Pair("TruecallerEvery10thCharacter",state.error))
                    }
                }
            }
        }
        viewModelScope.launch {
            _truecallerWordCounter.value = UiState.Loading
            repository.getBlogContent { state ->
                when(state) {
                    is UiState.Success -> {
                        truecallerWordCounterRequest(state.data.content)
                    }
                    is UiState.Failure -> {
                        _error.postValue(Pair("TruecallerWordCounter",state.error))
                    }
                }
            }
        }
    }

    fun truecaller10thCharacterRequest(data: String){
        _truecaller10thCharacter.postValue(UiState.Success(data.get(9)))
    }

    fun truecallerEvery10thCharacterRequest(data: String) {
        /*
       * Method 1 is more effiecent than method 2 because the number of iteration based on calculated limit
       * e-g If the chars are 600 then we need to iterate only 60 times to find the 10th char whereas
       * Method 2 encourage to iterate on each charcters so that is mean the loop will run up to 600 times
       * if the condition of index mathcing with 10th position so that will aslo cost to time and complexity
       * */

        val limit = data.length/10 // Finding iteration limit
        var method1Str = ""
        var tenthIndexCounter = 9 // As string start from 0 index so 10 - 1 = 9
        for (i in 0 until limit){
            method1Str += data[tenthIndexCounter]
            tenthIndexCounter += 10
        }

        _truecallerEvery10thCharacter.postValue(UiState.Success(method1Str))

        //Not using below code just mentioning second method
        var method2Str = ""
        for (index in 1 until limit){
            if (index % 10 == 0){
                method2Str += data[index-1]
            }
        }
    }

    @SuppressLint("NewApi")
    fun truecallerWordCounterRequest(data: String){
        val words = data.split(" ")
        val groups = words.groupBy { it }
        var words_count = ""
        groups.forEach { s, list ->
            words_count += String.format("%s = %d",s,list.size) + "\n"
        }
        _truecallerWordCounter.postValue(UiState.Success(words_count))
    }

}