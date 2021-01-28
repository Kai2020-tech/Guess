package com.example.guess

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GuessViewModel : ViewModel() {
    val counter = MutableLiveData<Int>()

    init {
        counter.value = 0
    }

    fun guess(num: Int) {
        var n = counter.value ?: 0
        n++
        counter.value = n
    }
}