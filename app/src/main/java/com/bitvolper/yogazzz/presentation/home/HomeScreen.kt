package com.bitvolper.yogazzz.presentation.home

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.categorydetail.CategoryDetailActivity
import com.bitvolper.yogazzz.presentation.serenitydetail.SerenityDetailActivity

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues(), onClick: () -> Unit = { }) {

    val context = LocalContext.current

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = paddingValues.calculateBottomPadding(),
            start = 16.dp, end = 16.dp
        )
        .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Card(modifier = modifier
            .fillMaxWidth()
            .requiredHeight(200.dp)) {

            Box {

                Image(painter = painterResource(id = R.drawable.ic_serenity),
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.BottomEnd)
                        .size(300.dp),
                    contentScale = ContentScale.Crop)

                Column(modifier = modifier
                    .padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

                    Text(text = "Serenity Flow:",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold)

                    Text(text = "Yoga For Beginner",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold)

                    Button(onClick = {
//                        SerenityDetailActivity.startActivity(context as Activity)
                        onClick()
                    }) {
                        Text(text = "Get Started")
                    }

                }
            }
        }

        YogaCategoryCompose()

        YogaRecommendationCompose()
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun YogaCategoryCompose(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val categories = listOf<YogaCategory>(
        YogaCategory(title = "Improved\nFlexibility", image = R.drawable.ic_flexibility),
        YogaCategory(title = "Stress\nReduction", image = R.drawable.ic_reduction),
        YogaCategory(title = "Improved\nPosture", image = R.drawable.ic_posture),
        YogaCategory(title = "Recovery", image = R.drawable.ic_recovery),
        YogaCategory(title = "Mindfulness &\nPresence", image = R.drawable.ic_mindulness),
        YogaCategory(title = "Spiritual\nGrowth", image = R.drawable.ic_spiritual),
        YogaCategory(title = "Emotional\nBalance", image = R.drawable.ic_balance),
        YogaCategory(title = "Enhanced\nSleep\nQuality", image = R.drawable.ic_sleep),
    )

    LazyVerticalGrid(modifier = modifier.requiredHeight(514.dp), columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        userScrollEnabled = false) {

        items(categories.size) {
            Card(onClick = { CategoryDetailActivity.startActivity(context as Activity) }, modifier = modifier.requiredHeight(120.dp)) {
                Box(modifier = modifier.fillMaxSize()) {
                    Image(painter = painterResource(id = categories[it].image),
                        contentDescription = null, 
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.End))
                    Text(text = categories[it].title, style = MaterialTheme.typography.titleMedium, modifier = modifier.padding(8.dp), lineHeight = 30.sp)
                }
            }
        }
    }
}

data class YogaCategory(
    val title: String,
    val image: Int
)

data class Recommendation(
    val title: String,
    val duration: String,
    val experienceLevel: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun RecommendationCard(modifier: Modifier = Modifier,
                               title: String = "Yoga Exercise",
                               duration: String = "10 mins",
                               experienceLevel: String = "Beginner",
                               ) {

    val context = LocalContext.current

    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {

        Card(onClick = { }, modifier = modifier.padding(8.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outlineVariant)) {
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

        Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = null)

    }
}

@Preview(showBackground = true)
@Composable
private fun YogaRecommendationCompose() {

    val recommendation = listOf<Recommendation>(
        Recommendation(title = "Yoga Exercise", duration = "10 mins", experienceLevel = "Beginner"),
        Recommendation(title = "Yoga Exercise", duration = "10 mins", experienceLevel = "Beginner"),
        Recommendation(title = "Yoga Exercise", duration = "10 mins", experienceLevel = "Beginner"),
        Recommendation(title = "Yoga Exercise", duration = "10 mins", experienceLevel = "Beginner")
    )

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Recommendation For You",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "View All",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )

            Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = null,
                tint = MaterialTheme.colorScheme.primary)

        }

        recommendation.forEach {
            RecommendationCard()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        HomeScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        HomeScreen()
    }
}