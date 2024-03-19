package com.bitvolper.yogazzz.presentation.serenitydetail.yogaexercise

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun PauseYogaScreen(modifier: Modifier = Modifier,
                    paddingValues: PaddingValues = PaddingValues(),
                    currentYogaExercise: ExerciseUIState = ExerciseUIState(),
                    onResumeClick: () -> Unit = { },
                    onRestartClick: () -> Unit = { }) {

    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            add(VideoFrameDecoder.Factory())
        }
        .build()


    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding() + 64.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {

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
            contentScale = ContentScale.Crop)

        Column(modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currentYogaExercise.exercise.title ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold
                )

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.Info, contentDescription = null)
                }
            }

            Text(
                text = currentYogaExercise.exerciseTimer.toString(),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = modifier.weight(1f))

        Button(onClick = onResumeClick, modifier = modifier.fillMaxWidth().requiredHeight(50.dp)) {
            Text(text = "RESUME")
        }

        OutlinedButton(onClick = onRestartClick, modifier = modifier.fillMaxWidth().requiredHeight(50.dp)) {
            Text(text = "Restart")
        }
        OutlinedButton(onClick = { (context as Activity).finish() }, modifier = modifier.fillMaxWidth().requiredHeight(50.dp)) {
            Text(text = "Quit")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PauseYogaLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        PauseYogaScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PauseYogaDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        PauseYogaScreen()
    }
}
