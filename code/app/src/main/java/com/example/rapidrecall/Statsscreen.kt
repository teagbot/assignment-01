package com.example.rapidrecall

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatsScreen(stats: Stats) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Total Games",
            fontSize = 30.sp
        )
        Text(
            text = stats.totalGames.toString(),
            fontSize = 90.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Wins",
            fontSize = 30.sp
        )
        Text(
            text = stats.totalWins.toString(),
            fontSize = 90.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Win %",
            fontSize = 30.sp
        )
        Text(
            text = "%.2f".format(stats.winPercent),
            fontSize = 90.sp
        )
    }
}