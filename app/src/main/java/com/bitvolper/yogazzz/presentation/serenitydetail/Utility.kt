package com.bitvolper.yogazzz.presentation.serenitydetail

fun sumTimeListToMinutes(timeList: List<String>): Int {
    var totalSeconds = 0
    for (time in timeList) {
        val (minutes, seconds) = time.split(": ").map { it.toInt() }
        totalSeconds += minutes * 60 + seconds
    }
    val totalMinutes = totalSeconds / 60
    return totalMinutes
}