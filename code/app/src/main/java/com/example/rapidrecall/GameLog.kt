package com.example.rapidrecall

import androidx.compose.runtime.mutableStateListOf

class GameLog {
    private val _results = mutableStateListOf<Result>()

    val results: List<Result>
        get() = _results

    fun addResult(result: Result) {
        _results.add(result)
    }
}