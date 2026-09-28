package com.example.rapidrecall

data class Stats(
    val totalGames: Int,
    val totalWins: Int,
) {

    val winPercent: Float
        get() = if (totalGames == 0) 0f else totalWins * 100f / totalGames

    companion object {
        fun from(results: List<Result>) = Stats(
            totalGames = results.size,
            totalWins = results.count { it.correct }
        )
    }

}