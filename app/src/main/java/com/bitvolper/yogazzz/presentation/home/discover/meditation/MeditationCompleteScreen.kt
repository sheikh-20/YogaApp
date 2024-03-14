package com.bitvolper.yogazzz.presentation.home.discover.meditation

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.Meditation
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeditationCompleteScreen(modifier: Modifier = Modifier,
                             paddingValues: PaddingValues = PaddingValues(),
                             meditation: Meditation.Data = Meditation.Data(),
                             onCloseButtonClick: () -> Unit = { },
                             onCompleteClick: (String) -> Unit = { _ ->  }) {

    BackHandler { onCloseButtonClick() }

    val emojiList = listOf(
        R.drawable.ic_emoji1,
        R.drawable.ic_emoji2,
        R.drawable.ic_emoji3,
        R.drawable.ic_emoji4,
        R.drawable.ic_emoji5,
        R.drawable.ic_emoji6)

    var selectedEmoji by remember { mutableIntStateOf(R.drawable.ic_emoji1) }

    Column(modifier = modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.TopCenter)
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = 20.dp
        ),
        verticalArrangement = Arrangement.spacedBy(20.dp)) {

        Icon(imageVector = Icons.Rounded.Check,
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(50)
                )
                .padding(20.dp)
                .size(30.dp))

        Text(text = "Well Done",
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold)

        Text(text = "You have completed the meditation.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center)


        Card(modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {

            Column(modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(text = "How do you feel after this session", modifier = modifier.fillMaxWidth(), textAlign = TextAlign.Center)

                Spacer(modifier = modifier.requiredHeight(4.dp))


                LazyVerticalGrid(columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {

                    items(emojiList.size) {

                        Card(onClick = { selectedEmoji = emojiList[it] },
                            border = BorderStroke(width = if (selectedEmoji == emojiList[it]) 1.dp else 0.dp, color = if (selectedEmoji == emojiList[it]) MaterialTheme.colorScheme.primary else Color.Transparent),
                            ) {
                            Image(painter = painterResource(id = emojiList[it]), contentDescription = null,
                                modifier = modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                                    .size(70.dp).padding(8.dp),
                                contentScale = ContentScale.Crop,
                                )
                        }
                    }
                }
            }
        }


        Spacer(modifier = modifier.weight(1f))

        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

            Divider(modifier = modifier.fillMaxWidth())

            Row(modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                Button(
                    onClick = { onCompleteClick(meditation.id ?: return@Button) },
                    modifier = modifier
                        .weight(1f)
                        .requiredHeight(50.dp)) {

                    Text(text = "Finish")

                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MeditationCompleteLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        MeditationCompleteScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MeditationCompleteDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        MeditationCompleteScreen()
    }
}