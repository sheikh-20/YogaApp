package com.bitvolper.yogazzz.presentation.home

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.bitvolper.yogazzz.R

@Composable
fun HomeApp(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            HomeTopAppBar()
        }
    ) { paddingValues ->
        HomeScreen(paddingValues = paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar() {

    CenterAlignedTopAppBar(title = {
        Text(
            text = stringResource(id = R.string.app_name),
            fontWeight = FontWeight.SemiBold
        )
    })
}