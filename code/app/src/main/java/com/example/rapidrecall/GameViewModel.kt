package com.example.rapidrecall

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

// Manages the current phase and game information such as sequence length, sequence, guess, etc
class GameViewModel : ViewModel() {
    val gameLog = GameLog()

    var phase by mutableStateOf<GamePhase>(GamePhase.Idle)

    var sequenceLength by mutableIntStateOf(SEQUENCE_MIN)

    val gameInProgress: Boolean
        get() = phase != GamePhase.Idle

    fun incrementLength() {
        if (!gameInProgress && sequenceLength < SEQUENCE_MAX) sequenceLength++
    }

    fun decrementLength() {
        if (!gameInProgress && sequenceLength > SEQUENCE_MIN) sequenceLength--
    }

    fun startGame() {
        if (gameInProgress) return
        phase = GamePhase.Countdown(randomDigitSequence(sequenceLength))
    }

    fun onCountdownFinished() {
        val current = phase as? GamePhase.Countdown ?: return
        phase = GamePhase.Showing(current.target)
    }

    fun onSequenceFinished() {
        val current = phase as? GamePhase.Showing ?: return
        phase = GamePhase.Input(current.target)
    }

    fun submitGuess(guess: String) {
        val current = phase as? GamePhase.Input ?: return
        val result = Result(
            target = current.target,
            guess = guess,
            timestampMillis = System.currentTimeMillis()
        )
        gameLog.addResult(result)
        phase = GamePhase.ShowingResult(result)
    }

    fun dismissResult() {
        phase = GamePhase.Idle
    }
}