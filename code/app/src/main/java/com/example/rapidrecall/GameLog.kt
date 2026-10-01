package com.example.rapidrecall

import androidx.compose.runtime.mutableStateListOf

// Stores a list of results, as well as stats for the Stats class to derive from
class GameLog {
    private val _results = mutableStateListOf<Result>()

    val results: List<Result>
        get() = _results

    val stats: Stats
        get() = Stats.from(_results)

    fun addResult(result: Result) {
        _results.add(result)
    }
}