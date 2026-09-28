package com.example.rapidrecall

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatsScreen(stats: Stats) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Total Games: ${stats.totalGames}")
        Text(text = "Wins: ${stats.totalWins}")
        Text(text = "Win %: ${"%.2f".format(stats.winPercent)}")
    }
}