package com.bitvolper.yogazzz.presentation.home.recommendation

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RecommendationApp(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            RecommendationTopAppBar()
        }
    ) { paddingValues ->
        RecommendationScreen(paddingValues = paddingValues)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecommendationTopAppBar() {
    val context = LocalContext.current

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Recommended For You",
                fontWeight = FontWeight.SemiBold)
        },
        navigationIcon = {
            IconButton(onClick = { (context as Activity).finish() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier.padding(horizontal = 4.dp)) {
                Icon(imageVector = Icons.Rounded.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
            }
        }
    )
}