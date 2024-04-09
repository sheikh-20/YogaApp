package com.bitvolper.yogazzz.utility

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

@Composable
fun SetLanguage(languageIndex: Int = 0) {
    val locale = Locale(
        when (languageIndex) {
            0 -> "en"
            else -> "es"
        }
    )
    Locale.setDefault(locale)
    val configuration = LocalConfiguration.current
    configuration.setLocale(locale)

    val resources = LocalContext.current.resources
    resources.updateConfiguration(configuration, resources.displayMetrics)

}