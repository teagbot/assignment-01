package com.example.rapidrecall

const val SEQUENCE_MIN = 1
const val SEQUENCE_MAX = 10
const val SEQUENCE_SPEED = 1

// generate a random sequence of a given length
fun randomDigitSequence(length: Int): String = (1..length).map { (0..9).random() }.joinToString("")