package com.bitvolper.yogazzz.presentation.home.recommendation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.bitvolper.yogazzz.base.BaseActivity
import com.bitvolper.yogazzz.presentation.home.notification.NotificationApp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

class RecommendationActivity: BaseActivity() {

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, RecommendationActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentStatusBar()

        setContent {
            YogaAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecommendationApp()
                }
            }
        }
    }
}