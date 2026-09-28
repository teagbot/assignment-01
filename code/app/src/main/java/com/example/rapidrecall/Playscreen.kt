package com.example.rapidrecall

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun PlayScreen(viewModel: GameViewModel) {
    Column {
        GameField(
            phase = viewModel.phase,
            onCountDownFinished = viewModel::onCountdownFinished,
            onSequenceFinished = viewModel::onSequenceFinished,
            onSubmitGuess = viewModel::submitGuess,
            onDismissResult = viewModel::dismissResult,
        )

        Spacer(modifier = Modifier.height(8.dp))

        LengthSelector(
            length = viewModel.sequenceLength,
            enabled = !viewModel.gameInProgress,
            onDecrement = viewModel::decrementLength,
            onIncrement = viewModel::incrementLength
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            PrimaryButton(
                text = "Start",
                onClick = viewModel::startGame,
                enabled = !viewModel.gameInProgress,
                modifier = Modifier.width(300.dp)
            )
        }
    }
}

@Composable
fun GameField(
    phase: GamePhase,
    onCountDownFinished: () -> Unit,
    onSequenceFinished: () -> Unit,
    onSubmitGuess: (String) -> Unit,
    onDismissResult: () -> Unit,
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(300.dp)
            .border(BorderStroke(2.dp, Color.Black))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            when (phase) {
                GamePhase.Idle -> Unit
                is GamePhase.Countdown -> CircleCountDown(onCountDownFinished)
                is GamePhase.Showing -> DisplaySequence(phase.target, onSequenceFinished)
                is GamePhase.Input -> GuessInput(onSubmitGuess)
                is GamePhase.ShowingResult -> ResultSummary(phase.result, onDismissResult)
            }
        }
    }
}

@Composable
private fun CircleCountDown(onFinished: () -> Unit) {
    var greenCount by remember { mutableIntStateOf(0) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        repeat(3) { index ->
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = if (index < greenCount) Color.Green else Color.Red,
                        shape = CircleShape
                    )
            )
        }
    }

    LaunchedEffect(Unit) {
        repeat(3) {
            delay(750L)
            greenCount++
        }
        delay(750L)
        onFinished()
    }
}

@Composable
private fun DisplaySequence(
    sequence: String,
    onFinished: () -> Unit
) {
    var shown by remember { mutableStateOf(" ") }

    Text(text = shown, fontSize = 128.sp)

    LaunchedEffect(0) {
        for (digit in sequence) {
            shown = digit.toString()
            delay(1000L / SEQUENCE_SPEED)
            shown = " "
            delay(250L / SEQUENCE_SPEED)
        }
        onFinished()
    }
}

@Composable
private fun GuessInput(
    onSubmit: (String) -> Unit
) {
    var guess by remember { mutableStateOf("")}

    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Text(text = "Enter sequence:")

        TextField(
            value = guess,
            onValueChange = { input ->
                if (input.all { it.isDigit() }) {
                    guess = input
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        PrimaryButton(
            text = "Confirm",
            onClick = { onSubmit(guess) },
            enabled = guess.isNotEmpty(),
            modifier = Modifier.width(100.dp)
        )
    }
}

@Composable
fun ResultSummary(
    result: Result,
    onDone: () -> Unit
) {
    Text(text = "Result: ${ if (result.correct) "Correct!" else "Incorrect"}")
    Text(text = "Your guess: ${result.guess}")
    Text(text = "Correct sequence: ${result.target}")

    PrimaryButton(
        text = "Done",
        onClick = onDone,
        modifier = Modifier.width(100.dp)
    )
}

@Composable
private fun LengthSelector(
    length: Int,
    enabled: Boolean,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        PrimaryButton(
            text = "-",
            onClick = onDecrement,
            enabled = enabled,
            containerColor = Color.Red,
            fontSize = 24
        )

        Spacer(modifier = Modifier.width(20.dp))

        Box(
            modifier = Modifier
                .width(100.dp)
                .border(BorderStroke(2.dp, Color.Black))
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = length.toString(), fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.width(20.dp))

        PrimaryButton(
            text = "+",
            onClick = onIncrement,
            enabled = enabled,
            containerColor = Color.Green,
            fontSize = 24
        )

    }
}