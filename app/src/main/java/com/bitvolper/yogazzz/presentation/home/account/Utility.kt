package com.bitvolper.yogazzz.presentation.home.account

import android.app.Activity
import android.content.Context
import com.google.android.play.core.review.ReviewManagerFactory
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun Activity.showFeedbackDialog() {
    val reviewManager = ReviewManagerFactory.create(this.applicationContext)
    reviewManager.requestReviewFlow().addOnCompleteListener {
        if (it.isSuccessful) {
            reviewManager.launchReviewFlow(this, it.result)
        }
    }
}


fun Long.millisecondToDate(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    val dateTime = Date(this)
    return formatter.format(dateTime)
}