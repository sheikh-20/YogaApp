package com.bitvolper.yogazzz.presentation.serenitydetail.yogaexercise

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun YogaExerciseApp(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { YogaExerciseTopAppBar() }
    ) { paddingValues ->
        StartYogaScreen(paddingValues = paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun YogaExerciseTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            LinearProgressIndicator(modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(10.dp)
                .padding(horizontal = 30.dp), progress = 0f, strokeCap = StrokeCap.Round)
        },
        navigationIcon = {
            IconButton(onClick = {  }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = {  }) {
                Icon(imageVector = Icons.Rounded.VolumeUp, contentDescription = null)
            }
        }
    )
}