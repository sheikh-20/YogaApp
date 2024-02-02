package com.bitvolper.yogazzz.presentation.accountsecurity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.bitvolper.yogazzz.base.BaseActivity
import com.bitvolper.yogazzz.presentation.notifications.NotificationsApp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

class AccountSecurityActivity: BaseActivity() {

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, AccountSecurityActivity::class.java)
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
                    AccountSecurityApp()
                }
            }
        }
    }
}