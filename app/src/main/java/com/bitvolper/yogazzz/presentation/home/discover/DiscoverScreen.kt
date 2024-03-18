package com.bitvolper.yogazzz.presentation.home.discover

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import com.bitvolper.yogazzz.domain.model.AdjustYogaLevel
import com.bitvolper.yogazzz.domain.model.FlexibilityStrength
import com.bitvolper.yogazzz.domain.model.PopularYoga
import com.bitvolper.yogazzz.domain.model.PopularYogaWithFlexibility
import com.bitvolper.yogazzz.domain.model.StressRelief
import com.bitvolper.yogazzz.presentation.bodyfocus.BodyFocusDetailActivity
import com.bitvolper.yogazzz.presentation.categorydetail.CategoryDetailActivity
import com.bitvolper.yogazzz.presentation.home.discover.adjust_yoga.AdjustYogaActivity
import com.bitvolper.yogazzz.presentation.home.discover.body_focus.BodyFocusActivity
import com.bitvolper.yogazzz.presentation.home.discover.flexiblity_strength.FlexibilityStrengthActivity
import com.bitvolper.yogazzz.presentation.home.discover.meditation.MeditationActivity
import com.bitvolper.yogazzz.presentation.home.discover.popular_yoga.PopularYogaActivity
import com.bitvolper.yogazzz.presentation.home.discover.stress_relief.StressReliefActivity
import com.bitvolper.yogazzz.presentation.home.recommendation.RecommendationActivity
import com.bitvolper.yogazzz.presentation.serenitydetail.SerenityDetailActivity
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.Body
import com.bitvolper.yogazzz.utility.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(modifier: Modifier = Modifier,
                   paddingValues: PaddingValues = PaddingValues(),
                   discoverUIState: Resource<PopularYogaWithFlexibility> = Resource.Loading) {

    Column(modifier = modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)) {
        when (discoverUIState) {
            is Resource.Loading -> {
                CircularProgressIndicator()
            }

            is Resource.Failure -> {
                Text(text = discoverUIState.throwable.toString())
            }

            is Resource.Success -> {
                Column(modifier = modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding(), start = 16.dp, end = 16.dp
                    )
                    .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(16.dp)) {

                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        label = { Text(text = "Search") },
                        shape = RoundedCornerShape(20),
                        modifier = modifier.fillMaxWidth(),
                        leadingIcon = { Icon(imageVector = Icons.Rounded.Search, contentDescription = null) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                    )

                    PopularYogaCompose(popularYoga = discoverUIState.data.popularYoga)
                    MeditationCard()
                    AdjustYogaLevelScreen(adjustYogaLevel = discoverUIState.data.adjustYogaLevel)
                    BodyFocusScreen()
                    FlexibilityStrengthScreen(flexibilityStrength = discoverUIState.data.flexibilityStrength)
                    StressReliefScreen(stressRelief = discoverUIState.data.stressRelief)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PopularYogaCompose(modifier: Modifier = Modifier, popularYoga: PopularYoga = PopularYoga(emptyList())) {

    val context = LocalContext.current

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Popular Yoga", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = modifier.weight(1f))

            Text(
                text = "View All",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    onClick = { PopularYogaActivity.startActivity(context as Activity) },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null)
            )

            Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    onClick = { PopularYogaActivity.startActivity(context as Activity) },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null)
            )
        }

        popularYoga.data?.forEach {
            PopularYogaCard(popularYoga = it ?: return)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PopularYogaCard(modifier: Modifier = Modifier,
                    popularYoga: PopularYoga.Data = PopularYoga.Data(title = "Yoga Exercise", duration = "10 mins", level = "Beginner")) {

    val context = LocalContext.current


    Row(modifier = modifier.fillMaxWidth()
        .clickable(
            onClick = {
                SerenityDetailActivity.startActivity(context as Activity, null)
            },
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {

        Card(onClick = { }, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outlineVariant)) {

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(popularYoga.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.ic_image_placeholder),
                contentDescription = null,
                modifier = modifier.size(height = 100.dp, width = 100.dp),
                contentScale = ContentScale.Crop)
        }

        Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = popularYoga.title ?: "",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = popularYoga.duration ?: "", style = MaterialTheme.typography.bodySmall)
                Text(text = ".", style = MaterialTheme.typography.bodySmall)
                Text(text = popularYoga.level ?: "", style = MaterialTheme.typography.bodySmall)
            }
        }

        Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun MeditationCard(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    Card(modifier = modifier
        .fillMaxWidth()
        .requiredHeight(120.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFb4a3ff)),

        onClick = { MeditationActivity.startActivity(context as Activity) }) {
        Row(modifier = modifier.fillMaxWidth()) {

            Column(modifier = modifier
                .weight(1f)
                .padding(start = 16.dp, top = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Meditation", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                Text(text = "Achieve a state of profound of calmness and clarity with our \"Calmness",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2, overflow = TextOverflow.Ellipsis)
            }

            Image(painter = painterResource(id = R.drawable.ic_meditation),
                contentDescription = null,
                modifier = modifier.fillMaxHeight().offset(x = 10.dp, y = 30.dp).graphicsLayer(
                    scaleX = 2f,
                    scaleY = 2f,
                    translationX = 0f,
                    translationY = 0f,
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun AdjustYogaLevelScreen(modifier: Modifier = Modifier, adjustYogaLevel: AdjustYogaLevel = AdjustYogaLevel(emptyList())) {

    val context = LocalContext.current

    val level = listOf(Pair(0, "All"), Pair(1, "Beginner"), Pair(2, "Intermediate"), Pair(3, "Advanced"))
    var selectedIndex by remember { mutableIntStateOf(0) }

    val color = MaterialTheme.colorScheme.primary

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            Text(text = "Adjust Your Yoga Level", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = modifier.weight(1f))

            Text(
                text = "View All",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    onClick = { AdjustYogaActivity.startActivity(context as Activity) },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null)
            )

            Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    onClick = { AdjustYogaActivity.startActivity(context as Activity) },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null)
            )
        }

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(level.size) {
                Chip(onClick = { selectedIndex = level[it].first }, colors = ChipDefaults.chipColors(backgroundColor = if (selectedIndex == level[it].first) color else Color.Transparent), border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)) {
                    Text(text = level[it].second, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        adjustYogaLevel.data?.forEach {
            AdjustYogaLevelCard(adjustYogaLevel = it ?: return)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AdjustYogaLevelCard(modifier: Modifier = Modifier,
                        adjustYogaLevel: AdjustYogaLevel.Data = AdjustYogaLevel.Data(title = "Yoga Exercise", duration = "10 mins", level = "Beginner")) {

    val context = LocalContext.current


    Row(modifier = modifier.fillMaxWidth()
        .clickable(
            onClick = {
                SerenityDetailActivity.startActivity(context as Activity, null)
            },
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {

        Card(onClick = { }, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outlineVariant)) {

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(adjustYogaLevel.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.ic_image_placeholder),
                contentDescription = null,
                modifier = modifier.size(height = 100.dp, width = 100.dp),
                contentScale = ContentScale.Crop)
        }

        Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = adjustYogaLevel.title ?: "",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = adjustYogaLevel.duration ?: "", style = MaterialTheme.typography.bodySmall)
                Text(text = ".", style = MaterialTheme.typography.bodySmall)
                Text(text = adjustYogaLevel.level ?: "", style = MaterialTheme.typography.bodySmall)
            }
        }

        Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)

    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class,
    ExperimentalMaterial3Api::class
)
@Preview(showBackground = true)
@Composable
private fun BodyFocusScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Text(text = "Body Focus Area", style = MaterialTheme.typography.titleMedium)
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier.requiredHeight(320.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(Body.bodyPartsImage.size) {
                Card(modifier = modifier.requiredHeight(100.dp),
                    onClick = {
                        BodyFocusDetailActivity.startActivity(
                            activity = context as Activity,
                            title = Body.bodyPartsImage[it].part,
                            image = Body.bodyPartsImage[it].image,
                            color = Body.bodyPartsImage[it].color)
                    }) {

                    Box() {
                        Image(painter = painterResource(id = Body.bodyPartsImage[it].image),
                            contentDescription = null,
                            modifier = modifier.fillMaxSize().wrapContentSize(align = Alignment.BottomEnd).size(120.dp),
                            contentScale = ContentScale.Crop)

                        Text(text = Body.bodyPartsImage[it].part, style = MaterialTheme.typography.bodyLarge, modifier = modifier.padding(16.dp))

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun FlexibilityStrengthScreen(modifier: Modifier = Modifier, flexibilityStrength: FlexibilityStrength = FlexibilityStrength(emptyList())) {


    val context = LocalContext.current

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            Text(text = "Flexibility & Strength", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = modifier.weight(1f))

            Text(
                text = "View All",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    onClick = { FlexibilityStrengthActivity.startActivity(context as Activity) },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null)
            )

            Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    onClick = { FlexibilityStrengthActivity.startActivity(context as Activity)  },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null)
            )
        }

        flexibilityStrength.data?.forEach {
            FlexibilityStrengthCard(flexibilityStrength = it ?: return)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FlexibilityStrengthCard(modifier: Modifier = Modifier,
                            flexibilityStrength: FlexibilityStrength.Data = FlexibilityStrength.Data(title = "Yoga Exercise", duration = "10 mins", level = "Beginner")) {

    val context = LocalContext.current


    Row(modifier = modifier.fillMaxWidth()
        .clickable(
            onClick = {
                SerenityDetailActivity.startActivity(context as Activity, null)
            },
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {

        Card(onClick = { }, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outlineVariant)) {

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(flexibilityStrength.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.ic_image_placeholder),
                contentDescription = null,
                modifier = modifier.size(height = 100.dp, width = 100.dp),
                contentScale = ContentScale.Crop)
        }

        Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = flexibilityStrength.title ?: "",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = flexibilityStrength.duration ?: "", style = MaterialTheme.typography.bodySmall)
                Text(text = ".", style = MaterialTheme.typography.bodySmall)
                Text(text = flexibilityStrength.level ?: "", style = MaterialTheme.typography.bodySmall)
            }
        }

        Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun StressReliefScreen(modifier: Modifier = Modifier, stressRelief: StressRelief = StressRelief(emptyList())) {

    val context = LocalContext.current

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            Text(text = "Stress Relief", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = modifier.weight(1f))

            Text(
                text = "View All",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    onClick = { StressReliefActivity.startActivity(context as Activity) },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null)
            )

            Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    onClick = { StressReliefActivity.startActivity(context as Activity) },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null)
            )
        }

        stressRelief.data?.forEach {
            StressReliefCard(stressRelief =  it ?: return)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun StressReliefCard(modifier: Modifier = Modifier,
                     stressRelief: StressRelief.Data = StressRelief.Data(title = "Yoga Exercise", duration = "10 mins", level = "Beginner")) {

    val context = LocalContext.current


    Row(modifier = modifier.fillMaxWidth()
        .clickable(
            onClick = {
                SerenityDetailActivity.startActivity(context as Activity, null)
            },
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {

        Card(onClick = { }, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outlineVariant)) {

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(stressRelief.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.ic_image_placeholder),
                contentDescription = null,
                modifier = modifier.size(height = 100.dp, width = 100.dp),
                contentScale = ContentScale.Crop)
        }

        Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stressRelief.title ?: "",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = stressRelief.duration ?: "", style = MaterialTheme.typography.bodySmall)
                Text(text = ".", style = MaterialTheme.typography.bodySmall)
                Text(text = stressRelief.level ?: "", style = MaterialTheme.typography.bodySmall)
            }
        }

        Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ExerciseCard(modifier: Modifier = Modifier,
                               title: String = "Yoga Exercise",
                               duration: String = "10 mins",
                               experienceLevel: String = "Beginner",
) {

    val context = LocalContext.current

    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {

        Card(onClick = { }, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outlineVariant)) {
            Image(painter = painterResource(id = R.drawable.ic_sedentary),
                contentDescription = null,
                modifier = modifier
                    .size(height = 100.dp, width = 100.dp)
                    .clip(RoundedCornerShape(20)))
        }

        Column(modifier = modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )

            Row {
                Text(text = duration, style = MaterialTheme.typography.bodySmall)
                Text(text = ".", style = MaterialTheme.typography.bodySmall)
                Text(text = experienceLevel, style = MaterialTheme.typography.bodySmall)
            }
        }

        Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DiscoverLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        DiscoverScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DiscoverDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        DiscoverScreen()
    }
}