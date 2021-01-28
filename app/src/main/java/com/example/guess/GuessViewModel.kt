package com.example.guess

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class GuessViewModel : ViewModel() {
    var secret = 0
    var count = 0
    val counter = MutableLiveData<Int>()
    val result = MutableLiveData<GameResult>()

    init {
        counter.value = count
        reset() //初始化時即呼叫reset(),以產生secret
    }

    fun guess(num: Int) {
        if (num == -111) result.value = GameResult.ENTER_NUMBER
        else {
            result.value = when (num - secret) {
                0 -> GameResult.RIGHT
                in 1..Int.MAX_VALUE -> GameResult.SMALLER
                else -> GameResult.BIGGER
            }
            count++
            counter.value = count
        }
    }

    fun reset() {
        secret = Random().nextInt(10) + 1
        count = 0
        counter.value = count
    }
}

enum class GameResult {
    BIGGER, SMALLER, RIGHT, ENTER_NUMBER
}