package com.bitvolper.yogazzz.presentation.home.discover.meditation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MeditationStartScreen(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues()) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Box(modifier = modifier
            .requiredHeight(250.dp)
            .fillMaxWidth()
            .background(color = Color(0xFFb4a3ff))) {
            Image(
                painter = painterResource(id = R.drawable.ic_meditation),
                contentDescription = null,
                modifier = modifier
                    .requiredHeight(250.dp)
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.BottomEnd)
                    .offset(x = 100.dp, y = 0.dp)
            )

            Column(modifier = modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.BottomStart)
                .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(text = "Meditation",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White)

                Text(text = "Achieve a state of profound calmness and clarity with our \"Calm Mind Meditation\"",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFf2f2f2)
                )
            }
        }

        Column(modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)) {

            Row(modifier = modifier.horizontalScroll(rememberScrollState()).padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Chip(onClick = { /*TODO*/ }) {
                    Text(text = "All")
                }
                Chip(onClick = { /*TODO*/ }) {
                    Text(text = "Calm")
                }
                Chip(onClick = { /*TODO*/ }) {
                    Text(text = "Breath")
                }
                Chip(onClick = { /*TODO*/ }) {
                    Text(text = "Gratitude")
                }
                Chip(onClick = { /*TODO*/ }) {
                    Text(text = "Morning")
                }
            }

            Row(modifier = modifier.horizontalScroll(rememberScrollState()).padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Chip(onClick = { /*TODO*/ }) {
                    Text(text = "All")
                }
                Chip(onClick = { /*TODO*/ }) {
                    Text(text = "< 10 mins")
                }
                Chip(onClick = { /*TODO*/ }) {
                    Text(text = "10 - 20 mins")
                }
                Chip(onClick = { /*TODO*/ }) {
                    Text(text = "> 20 mins")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MeditationStartLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        MeditationStartScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MeditationStartDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        MeditationStartScreen()
    }
}