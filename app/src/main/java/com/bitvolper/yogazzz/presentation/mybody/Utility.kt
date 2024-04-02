package com.bitvolper.yogazzz.presentation.mybody

import java.util.Calendar
import kotlin.math.ceil
import kotlin.math.pow

fun kgToLb(kg: Double): Double {
    val lbsPerKg = 2.20462
    val lb = kg * lbsPerKg
    return ceil(lb * 100) / 100
}

fun lbToKg(lb: Double): Double {
    val kgPerLb = 0.453592
    val kg = lb * kgPerLb
    return ceil(kg * 100) / 100
}

fun calculateAgeFromMilliseconds(milliseconds: Long): Int {
    val currentDate = Calendar.getInstance()
    val birthDate = Calendar.getInstance().apply {
        timeInMillis = milliseconds
    }

    var age = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
    if (currentDate.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
        age--
    }
    return age
}

fun cmToFeet(heightCm: Int): Double {
    return (heightCm / 30.48).round(1)
}

private fun Double.round(decimals: Int): Double {
    val multiplier = 10.0.pow(decimals)
    return kotlin.math.round(this * multiplier) / multiplier
}

fun feetToCm(feet: Double): Int {
    return (feet * 30.48).toInt() // 1 foot = 30.48 centimeters
}