package com.bitvolper.yogazzz.presentation.categorydetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.coroutineScope
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.base.BaseActivity
import com.bitvolper.yogazzz.presentation.billing.BillingSubscriptionApp
import com.bitvolper.yogazzz.presentation.home.notification.NotificationSettingsApp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.presentation.viewmodel.AccountViewModel
import com.bitvolper.yogazzz.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryDetailActivity: BaseActivity() {

    companion object {

        const val CATEGORY = "category"
        const val TITLE = "title"
        const val IMAGE = "image"
        const val COLOR = "color"

        fun startActivity(activity: Activity?,
                          category: String? = "improvedFlexibility",
                          title: String? = "Improved Flexibility",
                          @DrawableRes image: Int? = R.drawable.ic_category1,
                          @ColorRes color: Int? = R.color.category1) {
            val intent = Intent(activity, CategoryDetailActivity::class.java)
            intent.putExtra(CATEGORY, category)
            intent.putExtra(TITLE, title)
            intent.putExtra(IMAGE, image)
            intent.putExtra(COLOR, color)
            activity?.startActivity(intent)
        }
    }

    private val accountViewModel: AccountViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel.getYogaExerciseByCategory(intent.getStringExtra(CATEGORY) ?: return)

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
                            CategoryDetailApp()
                        }
                    }
                }
            }
        }
    }
}