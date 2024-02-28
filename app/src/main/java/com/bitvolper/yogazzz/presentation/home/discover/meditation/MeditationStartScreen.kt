package com.bitvolper.yogazzz.presentation.home.discover.meditation

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.Meditation
import com.bitvolper.yogazzz.domain.model.YogaRecommendation
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.Resource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MeditationStartScreen(modifier: Modifier = Modifier,
                          paddingValues: PaddingValues = PaddingValues(),
                          meditationUIState: Resource<Meditation> = Resource.Loading,
                          onCardClick: (Meditation.Data) -> Unit = { _ ->  }) {

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
                    .offset(x = 50.dp, y = 0.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.BottomStart)
                .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(text = "Meditation",
                    style = MaterialTheme.typography.headlineSmall,
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

            Row(modifier = modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Chip(onClick = { /*TODO*/ }, colors = ChipDefaults.chipColors(backgroundColor = Color.Transparent), border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)) {
                    Text(text = "All")
                }
                Chip(onClick = { /*TODO*/ }, colors = ChipDefaults.chipColors(backgroundColor = Color.Transparent), border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)) {
                    Text(text = "Calm")
                }
                Chip(onClick = { /*TODO*/ }, colors = ChipDefaults.chipColors(backgroundColor = Color.Transparent), border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)) {
                    Text(text = "Breath")
                }
                Chip(onClick = { /*TODO*/ }, colors = ChipDefaults.chipColors(backgroundColor = Color.Transparent), border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)) {
                    Text(text = "Gratitude")
                }
                Chip(onClick = { /*TODO*/ }, colors = ChipDefaults.chipColors(backgroundColor = Color.Transparent), border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)) {
                    Text(text = "Morning")
                }
            }

            Row(modifier = modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Chip(onClick = { /*TODO*/ }, colors = ChipDefaults.chipColors(backgroundColor = Color.Transparent), border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)) {
                    Text(text = "All")
                }
                Chip(onClick = { /*TODO*/ }, colors = ChipDefaults.chipColors(backgroundColor = Color.Transparent), border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)) {
                    Text(text = "< 10 mins")
                }
                Chip(onClick = { /*TODO*/ }, colors = ChipDefaults.chipColors(backgroundColor = Color.Transparent), border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)) {
                    Text(text = "10 - 20 mins")
                }
                Chip(onClick = { /*TODO*/ }, colors = ChipDefaults.chipColors(backgroundColor = Color.Transparent), border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)) {
                    Text(text = "> 20 mins")
                }
            }
        }

        Column(modifier = modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center)) {

            when (meditationUIState) {
                is Resource.Loading -> {
                    CircularProgressIndicator()
                }

                is Resource.Failure -> {
                    Text(text = meditationUIState.throwable.toString())
                }

                is Resource.Success -> {
                    LazyColumn(modifier = modifier.fillMaxSize(), contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                        items(meditationUIState.data.data?.size ?: return@LazyColumn) {
                            MeditationCard(meditation = meditationUIState.data.data[it] ?: return@items, onCardClick = onCardClick)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MeditationCard(modifier: Modifier = Modifier,
                   meditation: Meditation.Data = Meditation.Data(title = "Yoga Exercise", duration = "10 mins", category = "Calm", image = null),
                   onCardClick: (Meditation.Data) -> Unit = { _ ->  }) {

    val context = LocalContext.current


    Row(modifier = modifier.fillMaxWidth().clickable(onClick = { onCardClick(meditation) }, interactionSource = remember { MutableInteractionSource() }, indication = null),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)) {

        Box(modifier = modifier.requiredSize(height = 100.dp, width = 100.dp)) {

            Card(onClick = { }, modifier = modifier.padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outlineVariant)) {

                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(meditation.image)
                        .crossfade(true)
                        .build(),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    placeholder = painterResource(id = R.drawable.ic_image_placeholder),
                    contentDescription = null,
                    modifier = modifier
                        .size(height = 100.dp, width = 100.dp),
                    contentScale = ContentScale.Crop)
            }

            IconButton(onClick = { /*TODO*/ },
                modifier = modifier.fillMaxSize()
                    .wrapContentSize(align = Alignment.Center)
                    .background(color = Color(0xFFE5E5E5), shape = RoundedCornerShape(50))) {
                Icon(imageVector = Icons.Rounded.PlayArrow, contentDescription = null, modifier = modifier.size(30.dp), tint = MaterialTheme.colorScheme.primary)
            }

        }

        Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = meditation.title ?: "",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = meditation.duration ?: "", style = MaterialTheme.typography.bodyMedium)
                Text(text = ".", style = MaterialTheme.typography.bodySmall)
                Text(text = meditation.category ?: "", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)

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