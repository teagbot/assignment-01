package com.example.rapidrecall

// Contains the possible states of the game. Passes necessary information.
sealed interface GamePhase {
    data object Idle : GamePhase
    data class Countdown(val target: String) : GamePhase
    data class Showing(val target: String) : GamePhase
    data class Input(val target: String) : GamePhase
    data class ShowingResult(val result: Result) : GamePhase
}