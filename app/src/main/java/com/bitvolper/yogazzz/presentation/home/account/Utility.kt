package com.bitvolper.yogazzz.presentation.home.account

import android.app.Activity
import android.content.Context
import com.google.android.play.core.review.ReviewManagerFactory

fun Activity.showFeedbackDialog() {
    val reviewManager = ReviewManagerFactory.create(this.applicationContext)
    reviewManager.requestReviewFlow().addOnCompleteListener {
        if (it.isSuccessful) {
            reviewManager.launchReviewFlow(this, it.result)
        }
    }
}