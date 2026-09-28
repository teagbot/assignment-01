package com.example.rapidrecall

data class Result (
    val target: String,
    val guess: String,
    val timestampMillis: Long
){
     val sequenceLength: Int
         get() = target.length

    val correct: Boolean
        get() = guess == target
}