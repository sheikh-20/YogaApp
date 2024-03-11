package com.bitvolper.yogazzz.presentation.serenitydetail.yogaexercise

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.VideoFrameDecoder
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.presentation.viewmodel.ExerciseUIState

@Composable
fun NextYogaScreen(modifier: Modifier = Modifier,
                   paddingValues: PaddingValues = PaddingValues(),
                   currentYogaExercise: ExerciseUIState = ExerciseUIState(),
                   onSkipClick: () -> Unit = {  }) {

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            add(VideoFrameDecoder.Factory())
        }
        .build()

    Column(modifier = modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.primary)
        .padding(
            top = paddingValues.calculateTopPadding(),
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.SpaceBetween) {


        Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Next 4/11",
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center)

            Row(modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically) {

                androidx.compose.material.Text(text = currentYogaExercise.exercise.title ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold)

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.Info, contentDescription = null)
                }
            }
        }
        
        Spacer(modifier = modifier.weight(1f))

        AsyncImage(
            model = currentYogaExercise.exercise.file,
            imageLoader = imageLoader,
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.ic_image_placeholder),
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .size(250.dp)
                .clip(RoundedCornerShape(10)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = modifier.weight(1f))

        Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(text = "Rest",
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold)

            Text(text = "00: 14",
                style = MaterialTheme.typography.displaySmall,
                modifier = modifier.fillMaxWidth(), 
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = modifier.requiredHeight(30.dp))

        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = modifier
                    .weight(1f)
                    .requiredHeight(50.dp),
                border = BorderStroke(2.dp, Color.White)) {

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = null, tint = Color.White)
                    androidx.compose.material.Text(text = "10s",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold)
                }
            }

            OutlinedButton(
                onClick = onSkipClick,
                modifier = modifier
                    .weight(1f)
                    .requiredHeight(50.dp),
                border = BorderStroke(0.dp, Color.Transparent),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    androidx.compose.material.Text(text = "Skip", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                    Icon(imageVector = Icons.Rounded.SkipNext, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NextYogaLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        NextYogaScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NextYogaDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        NextYogaScreen()
    }
}