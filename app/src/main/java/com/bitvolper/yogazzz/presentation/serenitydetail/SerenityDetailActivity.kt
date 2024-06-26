package com.bitvolper.yogazzz.presentation.serenitydetail

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
import com.bitvolper.yogazzz.presentation.categorydetail.CategoryDetailApp
import com.bitvolper.yogazzz.presentation.notifications.NotificationsApp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.presentation.viewmodel.AccountViewModel
import com.bitvolper.yogazzz.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SerenityDetailActivity: BaseActivity() {

    companion object {

        const val YOGA_ID = "yoga_id"

        fun startActivity(activity: Activity?, yogaId: String?) {
            val intent = Intent(activity, SerenityDetailActivity::class.java)
            intent.putExtra(YOGA_ID, yogaId)
            activity?.startActivity(intent)
        }
    }

    private val homeViewModel: HomeViewModel by viewModels()
    private val accountViewModel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel.getSerenityFlow(id = intent.getStringExtra(YOGA_ID) ?: "c0fdad9b-307c-4000-a342-346cb8f8abac")

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
                            SerenityDetailApp()
                        }
                    }
                }
            }
        }
    }
}