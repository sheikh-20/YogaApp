package com.bitvolper.yogazzz.presentation.home.discover.meditation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@Composable
fun MeditationPlayScreen(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues()) {
    Box(modifier = modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)) {
        Image(painter = painterResource(id = R.drawable.ic_yoga_exercise),
            contentDescription = null,
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.Crop)

        Column(modifier = modifier.padding(top = paddingValues.calculateTopPadding(), start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Text(text = "Mindful Moments\nMeditation",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = modifier.fillMaxWidth(),
                fontWeight = FontWeight.SemiBold)

            Row(modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                Text(text = "8 mins", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                Text(text = ".", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                Text(text = "Calm", style = MaterialTheme.typography.bodyMedium, color = Color.White)
            }

            Spacer(modifier = modifier.weight(1f))

            FloatingActionButton(onClick = { /*TODO*/ }, modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .size(100.dp), shape = RoundedCornerShape(50)) {
                Icon(imageVector = Icons.Rounded.Pause, contentDescription = null, modifier = modifier.size(60.dp))
            }

            Spacer(modifier = modifier.weight(1f))

            Column {
                Slider(value = 0f, onValueChange = {}, modifier = modifier.fillMaxWidth())

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "00:00", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                    Spacer(modifier = modifier.weight(1f))
                    Text(text = "08:00", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MeditationPlayLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        MeditationPlayScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MeditationPlayDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        MeditationPlayScreen()
    }
}