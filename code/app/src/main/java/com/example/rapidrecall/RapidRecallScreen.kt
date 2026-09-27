package com.example.rapidrecall

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.sp
import kotlin.math.pow
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.compareTo

const val SEQUENCE_MIN = 1
const val SEQUENCE_MAX = 10

@Composable
fun RapidRecallScreen (
    results: List<Result>,
    stats: Stats,
    onAddResult: (Result) -> Unit,
    modifier: Modifier = Modifier
) {
    var sequenceLength by remember { mutableIntStateOf(1) }

    var sequence by remember { mutableIntStateOf(-1) }

    var gameStarted by remember { mutableStateOf(false) }

    var displayingLog by remember { mutableStateOf(false) }

    var displayingStats by remember { mutableStateOf(false) }


    Column(modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    displayingStats = false
                    displayingLog = false
                }
            ) {
                Text("Play")
            }
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    displayingStats = true
                    displayingLog = false
                }
            ) {
                Text("Stats")
            }
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    displayingLog = true
                    displayingStats = false
                }
            ) {
                Text("Log")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (displayingLog) {
            DisplayLog(
                results,
                onFinishDisplayLog = { displayingLog = false }
            )
        } else if (displayingStats) {
            DisplayStats(
                stats,
                onFinishDisplayStats = { displayingStats = false }
            )
        } else {
            GameField(
                gameStarted,
                sequence,
                sequenceLength,
                onAddResult,
                onFinishGame = { gameStarted = false }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        if (sequenceLength > SEQUENCE_MIN) sequenceLength--
                    }
                ) {
                    Text("-")
                }

                Spacer(modifier = Modifier.width(20.dp))

                OutlinedTextField(
                    value = sequenceLength.toString(),
                    onValueChange = { sequenceLength = it.toInt()},
                    readOnly = true,
                    modifier = Modifier
                        .width(100.dp)
                )

                Spacer(modifier = Modifier.width(20.dp))

                Button(
                    onClick = {
                        if (sequenceLength < SEQUENCE_MAX) sequenceLength++
                    }
                ) {
                    Text("+")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .width(300.dp),
                    onClick = {
                        sequence = generateSequence(sequenceLength).toInt()
                        gameStarted = true
                    }
                ) {
                    Text("Start")
                }
            }
        }
    }
}

@Composable
fun GameField(
    gameStarted: Boolean,
    sequence: Int,
    sequenceLength: Int,
    onAddResult: (Result) -> Unit,
    onFinishGame: () -> Unit
) {

    var gameState by remember { mutableIntStateOf(0) }
    var result by remember { mutableStateOf<Result?>(null) }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .width(300.dp)
            .height(300.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            if (gameStarted) {
                if (gameState == 0) {
                    CircleCountDown(
                        onFinishCountdown = { gameState = 1 }
                    )
                }
                if (gameState == 1) {
                    DisplaySequence(
                        sequence,
                        sequenceLength,
                        onFinishSequence = { gameState = 2 }
                    )
                }
                if (gameState == 2) {
                    GetUserSequence(
                        onFinishUserSequence = { userSequence ->
                            gameState = 3;
                            result = Result(
                                sequenceLength = sequenceLength,
                                userSequence = userSequence,
                                targetSequence = sequence,
                                correct = userSequence == sequence,
                                timeStamp = currentTimeAndDateText()
                            )
                            onAddResult(result!!)
                        }
                    )
                }
                if (gameState == 3) {
                    DisplayResult(
                        result = result,
                        onFinishDisplayResult = {
                            gameState = 0;
                            onFinishGame()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CircleCountDown(
    onFinishCountdown: () -> Unit
) {
    val circleColors = remember { mutableStateListOf<Color>(Color.Red, Color.Red, Color.Red) }

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            color = circleColors[0],
            radius = 30.dp.toPx(),
            center = Offset(90.dp.toPx(), 50.dp.toPx())
        )
        drawCircle(
            color = circleColors[1],
            radius = 30.dp.toPx(),
            center = Offset(175.dp.toPx(), 50.dp.toPx())
        )
        drawCircle(
            color = circleColors[2],
            radius = 30.dp.toPx(),
            center = Offset(260.dp.toPx(), 50.dp.toPx())
        )
    }

    LaunchedEffect(0) {
        delay(1000L)
        circleColors[0] = Color.Green
        delay(1000L)
        circleColors[1] = Color.Green
        delay(1000L)
        circleColors[2] = Color.Green
        delay(1000L)
        onFinishCountdown()
    }

}

@Composable
fun DisplaySequence(
    sequence: Int,
    sequenceLength: Int,
    onFinishSequence: () -> Unit
) {
    var currentDigit by remember { mutableIntStateOf(0) }
    var currentDisplay by remember { mutableStateOf(" ")}

    val sequenceString = sequence.toString()

    Text(text = currentDisplay)

    LaunchedEffect(0) {
        while (currentDigit < sequenceLength) {
            currentDisplay = sequenceString[currentDigit].toString()
            delay(1000L)
            currentDisplay = " "
            delay(500L)
            currentDigit++
        }
        onFinishSequence()
    }
}

@Composable
fun GetUserSequence(
    onFinishUserSequence: (userSequence: Int) -> Unit
) {
    var userSequence by remember { mutableStateOf("")}

    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Text(text = "Enter sequence:")

        TextField(
            value = userSequence,
            onValueChange = { input ->
                if (input.all { it.isDigit() }) {
                    userSequence = input
                }
            }
        )

        Button(
            modifier = Modifier
                .width(100.dp),
            onClick = {
                onFinishUserSequence(userSequence.toInt())
            }
        ) {
            Text(text = "Confirm")
        }
    }
}

@Composable
fun DisplayResult(
    result: Result?,
    onFinishDisplayResult: () -> Unit
) {
    var isDoneViewing by remember { mutableStateOf(false) }

    Text(text = "Result: ${ if (result?.getCorrect() == true) {"Correct!"} else {"Incorrect"}}")

    Text(text = "Your guess: ${result?.getUserSequence().toString()}")

    Text(text = "Correct sequence: ${result?.getTargetSequence().toString()}")

    Button(
        modifier = Modifier
            .width(100.dp),
        onClick = {
            onFinishDisplayResult()
        }
    ) {
        Text(text = "Done")
    }
}

@Composable
fun DisplayLog(
    results: List<Result>,
    onFinishDisplayLog: () -> Unit
) {

    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        if (results.isEmpty()) {
            Text(text = "No games yet")
        }
        LazyColumn(modifier = Modifier.height(650.dp)) {
            itemsIndexed(results) { index, result ->
                ResultRow(result = result)

                if (index < results.lastIndex) {
                    HorizontalDivider()
                }
            }
        }

        Button(
            modifier = Modifier
                .width(100.dp),
            onClick = {
                onFinishDisplayLog()
            }
        ) {
            Text(text = "Done")
        }
    }
}

@Composable
fun DisplayStats(
    stats: Stats,
    onFinishDisplayStats: () -> Unit
) {

    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Text(text = "Total Games: ${stats.getTotalGames().toString()}")

        Text(text = "Wins: ${stats.getTotalWins().toString()}")

        Text(text = "Win %: ${stats.getWinPercent().toString()}")

        Button(
            modifier = Modifier
                .width(100.dp),
            onClick = {
                onFinishDisplayStats()
            }
        ) {
            Text(text = "Done")
        }
    }
}

@Composable
fun ResultRow(result: Result) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Date: ${result.getTimeStamp()}",
            fontSize = 15.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Length: ${result.getSequenceLength().toString()}",
            fontSize = 15.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Result: ${if(result.getCorrect()) {" Correct "} else {" Incorrect "}}",
            fontSize = 15.sp,
            modifier = Modifier.weight(1f)
        )

    }
}

fun currentTimeAndDateText(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    val currentDateTimeString = formatter.format(Date())

    return currentDateTimeString

}

fun generateSequence(sequenceLength: Int) : Long {
    val min = 10.0.pow(sequenceLength - 1).toLong()
    val max = 10.0.pow(sequenceLength).toLong() - 1

    return (min..max).random()
}

