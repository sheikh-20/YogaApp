package com.bitvolper.yogazzz.presentation.home.account.privacypolicy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.coroutineScope
import com.bitvolper.yogazzz.base.BaseActivity
import com.bitvolper.yogazzz.presentation.support.SupportActivity
import com.bitvolper.yogazzz.presentation.support.SupportApp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.presentation.viewmodel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PrivacyPolicyActivity: BaseActivity() {

    companion object {
        fun startActivity(activity: Activity?) {
            val intent = Intent(activity, PrivacyPolicyActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    private val accountViewModel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.coroutineScope.launch {
            accountViewModel.appThemeIndex.collect {
                setTransparentStatusBar(it.themeIndex)

                setContent {
                    YogaAppTheme(darkTheme = when (it.themeIndex) {
                        0 -> isSystemInDarkTheme()
                        1 -> false
                        else -> true
                    } ) {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            PrivacyPolicyApp()
                        }
                    }
                }
            }
        }
    }
}