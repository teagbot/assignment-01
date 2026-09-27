package com.example.rapidrecall

class Result (
    private val sequenceLength: Int,
    private val userSequence: Int,
    private val targetSequence: Int,
    private val correct: Boolean,
    private val timeStamp: String
){
    fun getSequenceLength(): Int {
        return sequenceLength
    }

    fun getUserSequence(): Int {
        return userSequence
    }

    fun getTargetSequence(): Int {
        return targetSequence
    }

    fun getCorrect(): Boolean {
        return correct
    }

    fun getTimeStamp(): String {
        return timeStamp
    }
}