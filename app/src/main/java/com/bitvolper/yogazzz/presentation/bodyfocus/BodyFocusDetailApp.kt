package com.bitvolper.yogazzz.presentation.bodyfocus

import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.bitvolper.yogazzz.presentation.categorydetail.CategoryDetailScreen
import com.bitvolper.yogazzz.presentation.viewmodel.HomeViewModel

@Composable
fun BodyFocusDetailApp(modifier: Modifier = Modifier,
                       homeViewModel: HomeViewModel = hiltViewModel()) {

    val yogaCategoryUIState by homeViewModel.yogaCategoryUIState.collectAsState()

    Scaffold(
        topBar = {
            BodyFocusTopAppBar()
        }
    ) { paddingValues ->
        BodyFocusDetailScreen(yogaCategoryUIState = yogaCategoryUIState)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BodyFocusTopAppBar() {
    val context = LocalContext.current

    CenterAlignedTopAppBar(
        title = {

        },
        navigationIcon = {
            IconButton(onClick = { (context as Activity).finish() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null, tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
    )
}
