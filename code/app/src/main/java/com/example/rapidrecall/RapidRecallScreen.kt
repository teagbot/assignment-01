package com.example.rapidrecall

import android.app.GameState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rapidrecall.ui.theme.RapidRecallTheme
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.mutableIntStateOf
import kotlin.math.pow

const val SEQUENCE_MIN = 1
const val SEQUENCE_MAX = 10

@Composable
fun RapidRecallScreen (
    modifier: Modifier = Modifier
) {
    var sequenceLength by remember { mutableIntStateOf(1) }

    var sequence by remember { mutableIntStateOf(-1) }

    var gameState by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {

                }
            ) {
                Text("Stats")
            }
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {

                }
            ) {
                Text("Log")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        GameField(gameState, sequence)

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
                }
            ) {
                Text("Start")
            }
        }
    }
}

@Composable
fun GameField(gameState: Int, sequence: Int) {
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
            Text(text = sequence.toString())
        }
    }
}

fun generateSequence(sequenceLength: Int) : Long {
    val min = 10.0.pow(sequenceLength - 1).toLong()
    val max = 10.0.pow(sequenceLength).toLong() - 1

    return (min..max).random()
}
