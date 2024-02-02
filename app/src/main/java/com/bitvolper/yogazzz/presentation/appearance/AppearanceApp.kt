package com.bitvolper.yogazzz.presentation.appearance

import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AppearanceApp(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            AppearanceTopAppBar()
        }
    ){ paddingValues ->
        AppearanceScreen(paddingValues = paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppearanceTopAppBar() {
    val context = LocalContext.current

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "App Appearance",
                fontWeight = FontWeight.SemiBold)
        },
        navigationIcon = {
            IconButton(onClick = { (context as Activity).finish() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        },
        actions = {

        }
    )
}