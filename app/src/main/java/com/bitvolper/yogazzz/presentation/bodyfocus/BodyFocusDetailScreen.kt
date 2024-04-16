package com.bitvolper.yogazzz.presentation.bodyfocus

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.SerenityData
import com.bitvolper.yogazzz.presentation.categorydetail.CategoryDetailActivity
import com.bitvolper.yogazzz.presentation.serenitydetail.SerenityDetailActivity
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.Resource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BodyFocusDetailScreen(modifier: Modifier = Modifier, yogaCategoryUIState: Resource<SerenityData> = Resource.Loading) {
    val context = LocalContext.current

    val level = listOf(
        Pair(0, stringResource(id = R.string.all)),
        Pair(1, stringResource(id = R.string.beginner)),
        Pair(2, stringResource(id = R.string.intermediate)),
        Pair(3, stringResource(id = R.string.advanced)))
    var selectedLevelIndex by remember { mutableIntStateOf(0) }

    val time = listOf(
        Pair(0, stringResource(id = R.string._10_mins)),
        Pair(1, stringResource(id = R.string._10_20_mins)),
        Pair(2, stringResource(id = R.string._20_mins)))
    var selectedTimeIndex by remember { mutableIntStateOf(0) }

    val color = MaterialTheme.colorScheme.primary


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Box(modifier = modifier
            .requiredHeight(250.dp)
            .fillMaxWidth()
            .background(
                color = colorResource(
                    id = (context as Activity).intent.getIntExtra(
                        BodyFocusDetailActivity.COLOR,
                        R.color.category1
                    )
                )
            )) {
            Image(
                painter = painterResource(id = (context as Activity).intent.getIntExtra(
                    BodyFocusDetailActivity.IMAGE, R.drawable.ic_category1)),
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
                Text(text = ((context as Activity).intent.getStringExtra(BodyFocusDetailActivity.TITLE)) ?: "Improved Flexibility",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White)

                Text(text = "Enhance agility and flexibility with dynamic stretches and flowing sequencesdxfgvdfg.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFf2f2f2)
                )
            }
        }


        Column(modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)) {

            LazyRow(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {

                items(level.size) {
                    Chip(onClick = { selectedLevelIndex = level[it].first },
                        colors = ChipDefaults.chipColors(backgroundColor = if (selectedLevelIndex == level[it].first) color else Color.Transparent),
                        border = BorderStroke(width = 1.dp, color = if (selectedLevelIndex == level[it].first) Color.Transparent else MaterialTheme.colorScheme.outline)
                    ) {
                        Text(text = level[it].second,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = if (selectedLevelIndex == level[it].first) Color.Black else Color.Unspecified)
                    }
                }
            }

            LazyRow(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {

                items(time.size) {
                    Chip(onClick = { selectedTimeIndex = time[it].first },
                        colors = ChipDefaults.chipColors(backgroundColor = if (selectedTimeIndex == time[it].first) color else Color.Transparent),
                        border = BorderStroke(width = 1.dp, color = if (selectedTimeIndex == level[it].first) Color.Transparent else MaterialTheme.colorScheme.outline)
                    ) {
                        Text(text = time[it].second,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = if (selectedTimeIndex == level[it].first) Color.Black else Color.Unspecified)
                    }
                }
            }
        }

        Column(modifier = modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center)) {

            when (yogaCategoryUIState) {
                is Resource.Loading -> {
                    CircularProgressIndicator()
                }

                is Resource.Failure -> {
                    Text(text = yogaCategoryUIState.throwable.toString())
                }

                is Resource.Success -> {
                    LazyColumn(modifier = modifier.fillMaxSize(), contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                        items(yogaCategoryUIState.data.data?.size ?: return@LazyColumn) {
                            BodyFocusCard(yoga = yogaCategoryUIState.data.data[it] ?: return@items)
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
private fun BodyFocusCard(modifier: Modifier = Modifier,
                               yoga: SerenityData.Data = SerenityData.Data()) {

    val context = LocalContext.current

    Row(modifier = modifier
        .fillMaxWidth()
        .clickable(
            onClick = {
                SerenityDetailActivity.startActivity(context as Activity, yoga.id)
            },
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)) {


        Card(onClick = { }, modifier = modifier.padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outlineVariant)) {

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(yoga.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.ic_image_placeholder),
                contentDescription = null,
                modifier = modifier
                    .size(height = 100.dp, width = 100.dp),
                contentScale = ContentScale.Crop)
        }

        Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = yoga.title ?: "",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = "${yoga.duration  ?: ""} mins", style = MaterialTheme.typography.bodyMedium)
                Text(text = ".", style = MaterialTheme.typography.bodySmall)
                Text(text = yoga.level ?: "", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BodyFocusDetailLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        BodyFocusDetailScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BodyFocusDetailDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        BodyFocusDetailScreen()
    }
}