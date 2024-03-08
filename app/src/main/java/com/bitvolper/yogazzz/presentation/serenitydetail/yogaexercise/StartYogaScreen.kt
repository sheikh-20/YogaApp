package com.bitvolper.yogazzz.presentation.serenitydetail.yogaexercise

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
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

@Composable
fun StartYogaScreen(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues()) {

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
        verticalArrangement = Arrangement.spacedBy(100.dp)) {

        AsyncImage(
            model = R.drawable.ic_yoga_exercise,
            imageLoader = imageLoader,
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.ic_image_placeholder),
            contentDescription = null,
            modifier = modifier.fillMaxWidth().size(250.dp).clip(RoundedCornerShape(10)),
            contentScale = ContentScale.Crop)

        Spacer(modifier = modifier.weight(1f))

        GetReadyCompose()
    }
}

@Preview(showBackground = true)
@Composable
private fun GetReadyCompose(modifier: Modifier = Modifier) {

    val unfilledColor = Color.Gray
    val filledColor = MaterialTheme.colorScheme.primary

    Column(modifier = modifier
        .fillMaxWidth()
        .wrapContentSize(align = Alignment.Center),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Get Ready!",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center)

        Row(modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Warrior 1",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold)

            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.Info, contentDescription = null)
            }
        }

        Box(modifier = modifier
            .fillMaxWidth()
            .size(150.dp)
            .padding(horizontal = 20.dp)) {

            Box(modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .size(150.dp)) {

                Canvas(
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                        .size(150.dp)
                        .padding(10.dp)
                ) {

                    drawArc(
                        color = unfilledColor,
                        0f,
                        360f,
                        false,
                        style = Stroke(12.dp.toPx(), cap = StrokeCap.Round),
                        size = Size(size.width, size.height)
                    )

                    drawArc(
                        color = filledColor,
                        90f,
                        200f,
                        false,
                        style = Stroke(12.dp.toPx(), cap = StrokeCap.Round),
                        size = Size(size.width, size.height)
                    )
                }

                androidx.compose.material3.Text(
                    text = "7",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center)
                )

            }

            IconButton(onClick = { /*TODO*/ }, modifier = modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.CenterEnd)) {
                Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun YogaStartCompose(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxWidth()
        .wrapContentSize(align = Alignment.Center),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Row(modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Warrior 1",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold)

            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.Info, contentDescription = null)
            }
        }

        Text(text = "00:46",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center)

        Button(onClick = { /*TODO*/ }, modifier = modifier.fillMaxWidth(), ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(imageVector = Icons.Rounded.Pause, contentDescription = null)
                Text(text = "PAUSE")
            }
        }

        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { /*TODO*/ }, modifier = modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Rounded.Pause, contentDescription = null)
                    Text(text = "PREVIOUS")
                }
            }

            OutlinedButton(onClick = { /*TODO*/ }, modifier = modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Rounded.Pause, contentDescription = null)
                    Text(text = "SKIP")
                }
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun StartYogaLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        StartYogaScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StartYogaDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        StartYogaScreen()
    }
}