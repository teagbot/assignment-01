package com.example.rapidrecall

class Stats {
    private var totalGames = 0
    private var totalWins = 0

    fun addGame(win: Boolean) {
        totalGames++
        if (win) totalWins++
    }

    fun getTotalGames(): Int {
        return totalGames
    }

    fun getTotalWins(): Int {
        return totalWins
    }

    fun getWinPercent(): Float {
        if (totalGames == 0) return 0.0F
        return ((totalWins.toFloat()/totalGames.toFloat())*100.0F)
    }


}