package com.bitvolper.yogazzz.presentation.home.account.aboutus

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.bitvolper.yogazzz.R

@Composable
fun AboutUsApp(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { AboutUsTopAppBar() }
    ) { paddingValues ->
        AboutUsScreen(paddingValues = paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutUsTopAppBar() {
    val context = LocalContext.current

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.about_us),
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