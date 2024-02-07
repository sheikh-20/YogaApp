package com.bitvolper.yogazzz.presentation.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bitvolper.yogazzz.base.BaseActivity
import com.bitvolper.yogazzz.presentation.accountsetup.AccountSetupApp
import com.bitvolper.yogazzz.presentation.onboarding.OnboardingApp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.presentation.viewmodel.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, HomeActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    private val onboardingViewModel: OnboardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                onboardingViewModel.loading.value
            }
        }
        setTransparentStatusBar()

        setContent {
            YogaAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (onboardingViewModel.getUserInfo() != null) {
                        HomeApp()
                    } else {
                        OnboardingApp()
                    }
                }
            }
        }
    }
}