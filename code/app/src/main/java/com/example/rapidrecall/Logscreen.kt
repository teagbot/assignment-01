package com.example.rapidrecall

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LogScreen(
    results: List<Result>
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        if (results.isEmpty()) {
            Text(text = "No games yet")
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(results) { index, result ->
                ResultRow(result)
                if (index < results.lastIndex) HorizontalDivider()
            }
        }
    }
}

@Composable
private fun ResultRow(result: Result) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Date: ${formatTimestamp(result.timestampMillis)}",
            fontSize = 15.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Length: ${result.sequenceLength}",
            fontSize = 15.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Result: ${if(result.correct) " Correct " else " Incorrect "}",
            fontSize = 15.sp,
            modifier = Modifier.weight(1f)
        )

    }
}

private fun formatTimestamp(millis: Long): String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(millis))