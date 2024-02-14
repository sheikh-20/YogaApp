package com.bitvolper.yogazzz.presentation.home.discover

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.Body

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues()) {
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

        PopularYogaComopose()
        MeditationCard()
        AdjustYogaLevelScreen()
        BodyFocusScreen()
        FlexibilityStrengthScreen()
        StressReliefScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun PopularYogaComopose(modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Popular Yoga", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = modifier.weight(1f))

            Text(text = "View All", style = MaterialTheme.typography.bodyMedium)

            Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = null)
        }

        ExerciseCard()

        ExerciseCard()
    }
}

@Preview(showBackground = true)
@Composable
private fun MeditationCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .fillMaxWidth()
        .requiredHeight(120.dp)) {
        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {

            Column(modifier = modifier
                .weight(1f)
                .padding(start = 16.dp, top = 16.dp)) {
                Text(text = "Meditation", style = MaterialTheme.typography.titleMedium)
                Text(text = "Achieve a state of profound of calmness and clarity with our \"Calmness",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2, overflow = TextOverflow.Ellipsis)
            }

            Image(painter = painterResource(id = R.drawable.ic_balance),
                contentDescription = null,
                modifier = modifier.fillMaxHeight(),
                )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun AdjustYogaLevelScreen(modifier: Modifier = Modifier) {

    val level = listOf<String>("All", "Beginner", "Intermediate", "Advanced")

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            Text(text = "Adjust Your Yoga Level", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = modifier.weight(1f))

            Text(text = "View All", style = MaterialTheme.typography.bodyMedium)

            Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = null)
        }

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(level.size) {
                Chip(onClick = { /*TODO*/ }) {
                    Text(text = level[it], style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        ExerciseCard()

        ExerciseCard()
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
private fun BodyFocusScreen(modifier: Modifier = Modifier) {

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Text(text = "Body Focus Area", style = MaterialTheme.typography.titleMedium)
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier.requiredHeight(320.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(Body.bodyPartsImage.size) {
                Card(modifier = modifier.requiredHeight(100.dp)) {
                    Row {
                        Text(text = Body.bodyPartsImage[it].part)
                        Image(painter = painterResource(id = Body.bodyPartsImage[it].image),
                            contentDescription = null,
                            modifier = modifier.fillMaxHeight(),
                            contentScale = ContentScale.Crop)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun FlexibilityStrengthScreen(modifier: Modifier = Modifier) {

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            Text(text = "Flexibility & Strength", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = modifier.weight(1f))

            Text(text = "View All", style = MaterialTheme.typography.bodyMedium)

            Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = null)
        }

        ExerciseCard()

        ExerciseCard()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun StressReliefScreen(modifier: Modifier = Modifier) {

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            Text(text = "Stress Relief", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = modifier.weight(1f))

            Text(text = "View All", style = MaterialTheme.typography.bodyMedium)

            Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = null)
        }

        ExerciseCard()

        ExerciseCard()
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