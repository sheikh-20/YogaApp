package com.bitvolper.yogazzz.presentation.home

import android.app.Activity
import android.content.res.Configuration
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Diamond
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.YogaCategory
import com.bitvolper.yogazzz.domain.model.YogaCategoryWithRecommendation
import com.bitvolper.yogazzz.domain.model.YogaRecommendation
import com.bitvolper.yogazzz.presentation.categorydetail.CategoryDetailActivity
import com.bitvolper.yogazzz.presentation.home.recommendation.RecommendationActivity
import com.bitvolper.yogazzz.presentation.serenitydetail.SerenityDetailActivity
import com.bitvolper.yogazzz.presentation.subscription.SubscriptionActivity
import com.bitvolper.yogazzz.utility.Resource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               paddingValues: PaddingValues = PaddingValues(),
               homeUIState: Resource<YogaCategoryWithRecommendation> = Resource.Loading) {

    val context = LocalContext.current

    Column(modifier = modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)) {
        when (homeUIState) {
            is Resource.Loading -> {
                CircularProgressIndicator()
            }
            is Resource.Failure -> {
                Text(text = homeUIState.throwable.toString())
            }
            is Resource.Success -> {
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
                        .requiredHeight(200.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {

                        Box {

                            Image(painter = painterResource(id = R.drawable.ic_serenity),
                                contentDescription = null,
                                modifier = modifier
                                    .fillMaxSize()
                                    .wrapContentSize(align = Alignment.BottomEnd)
                                    .size(350.dp)
                                    .offset(y = 0.dp, x = 50.dp),
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
                                    SerenityDetailActivity.startActivity(context as Activity, null)
                                }) {
                                    Text(text = "Get Started")
                                }
                            }
                        }
                    }

                    YogaCategoryCompose(yogaCategory = homeUIState.data.category ?: return)

                    YogaRecommendationCompose(recommendation = homeUIState.data.recommendation ?: return)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun YogaCategoryCompose(modifier: Modifier = Modifier,
                                yogaCategory: YogaCategory = YogaCategory(emptyList())
) {

    val context = LocalContext.current

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Card(onClick = {
                CategoryDetailActivity.startActivity(
                    activity = context as Activity,
                    category = "improvedFlexibility",
                    title = "Improved Flexibility",
                    image = R.drawable.ic_category1,
                    color = R.color.category1) },

                modifier = modifier
                    .weight(1f)
                    .requiredHeight(120.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.category1))) {
                Box(modifier = modifier.fillMaxSize()) {


                    Image(
                        painter = painterResource(id = R.drawable.ic_category1),
                        contentDescription = null,
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.End)
                            .size(150.dp))

                    Text(text = "Improved\nFlexibility", style = MaterialTheme.typography.titleMedium, modifier = modifier.padding(8.dp), lineHeight = 30.sp)
                }
            }

            Card(onClick = {
                CategoryDetailActivity.startActivity(
                    activity = context as Activity,
                    category = "stressReduction",
                    title = "Stress Reduction",
                    image = R.drawable.ic_category2,
                    color = R.color.category2)
                           },
                modifier = modifier
                    .weight(1f)
                    .requiredHeight(120.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.category2))) {
                Box(modifier = modifier.fillMaxSize()) {


                    Image(
                        painter = painterResource(id = R.drawable.ic_category2),
                        contentDescription = null,
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.End)
                            .size(150.dp))

                    Text(text = "Stress\nReduction", style = MaterialTheme.typography.titleMedium, modifier = modifier.padding(8.dp), lineHeight = 30.sp)
                }
            }
        }


        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Card(onClick = { CategoryDetailActivity.startActivity(context as Activity) }, modifier = modifier
                .weight(1f)
                .requiredHeight(120.dp), colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.category3))) {
                Box(modifier = modifier.fillMaxSize()) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_category3),
                        contentDescription = null,
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.End)
                            .size(150.dp))


                    Text(text = "Improved\nPosture", style = MaterialTheme.typography.titleMedium, modifier = modifier.padding(8.dp), lineHeight = 30.sp)
                }
            }

            Card(onClick = { CategoryDetailActivity.startActivity(context as Activity) }, modifier = modifier
                .weight(1f)
                .requiredHeight(120.dp), colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.category4))) {
                Box(modifier = modifier.fillMaxSize()) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_category4),
                        contentDescription = null,
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.End)
                            .size(150.dp))

                    Text(text = "Recovery", style = MaterialTheme.typography.titleMedium, modifier = modifier.padding(8.dp), lineHeight = 30.sp)
                }
            }
        }

        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Card(onClick = { CategoryDetailActivity.startActivity(context as Activity) }, modifier = modifier
                .weight(1f)
                .requiredHeight(120.dp), colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.category5))) {
                Box(modifier = modifier.fillMaxSize()) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_category5),
                        contentDescription = null,
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.End)
                            .size(150.dp))


                    Text(text = "Mindfulness\n& Presence", style = MaterialTheme.typography.titleMedium, modifier = modifier.padding(8.dp), lineHeight = 30.sp)
                }
            }

            Card(onClick = { CategoryDetailActivity.startActivity(context as Activity) }, modifier = modifier
                .weight(1f)
                .requiredHeight(120.dp), colors = CardDefaults.cardColors(containerColor = colorResource(R.color.category6))) {
                Box(modifier = modifier.fillMaxSize()) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_category6),
                        contentDescription = null,
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.End)
                            .size(150.dp))

                    Text(text = "Spritual\nGrowth", style = MaterialTheme.typography.titleMedium, modifier = modifier.padding(8.dp), lineHeight = 30.sp)
                }
            }
        }

        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Card(onClick = { CategoryDetailActivity.startActivity(context as Activity) }, modifier = modifier
                .weight(1f)
                .requiredHeight(120.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.category7))) {
                Box(modifier = modifier.fillMaxSize()) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_category7),
                        contentDescription = null,
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.End)
                            .size(150.dp))

                    Text(text = "Emotional\nBalance", style = MaterialTheme.typography.titleMedium, modifier = modifier.padding(8.dp), lineHeight = 30.sp)
                }
            }

            Card(onClick = { CategoryDetailActivity.startActivity(context as Activity) }, modifier = modifier
                .weight(1f)
                .requiredHeight(120.dp), colors = CardDefaults.cardColors(containerColor = colorResource(R.color.category8))) {
                Box(modifier = modifier.fillMaxSize()) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_category8),
                        contentDescription = null,
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.End)
                            .size(150.dp))


                    Text(text = "Enhanced\nSleep\nQuality", style = MaterialTheme.typography.titleMedium, modifier = modifier.padding(8.dp), lineHeight = 30.sp)
                }
            }
        }
    }

//    LazyVerticalGrid(modifier = modifier.requiredHeight(514.dp), columns = GridCells.Fixed(2),
//        verticalArrangement = Arrangement.spacedBy(10.dp),
//        horizontalArrangement = Arrangement.spacedBy(16.dp),
//        userScrollEnabled = false) {
//
//        items(yogaCategory.data?.size ?: return@LazyVerticalGrid) {
//            Card(onClick = { CategoryDetailActivity.startActivity(context as Activity) }, modifier = modifier.requiredHeight(120.dp)) {
//                Box(modifier = modifier.fillMaxSize()) {
//
//                    AsyncImage(
//                        model = ImageRequest.Builder(context = LocalContext.current)
//                            .data(yogaCategory.data[it]?.image)
//                            .crossfade(true)
//                            .build(),
//                        error = painterResource(id = R.drawable.ic_broken_image),
//                        placeholder = painterResource(id = R.drawable.ic_image_placeholder),
//                        contentDescription = null,
//                        modifier = modifier
//                            .fillMaxWidth()
//                            .wrapContentWidth(align = Alignment.End))
//
//                    Text(text = yogaCategory.data[it]?.title ?: "", style = MaterialTheme.typography.titleMedium, modifier = modifier.padding(8.dp), lineHeight = 30.sp)
//                }
//            }
//        }
//    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun RecommendationCard(modifier: Modifier = Modifier,
                       recommendation: YogaRecommendation.Data = YogaRecommendation.Data(title = "Yoga Exercise", duration = "10 mins", level = "Beginner")) {

    val context = LocalContext.current

    Row(modifier = modifier
        .fillMaxWidth()
        .clickable(
            onClick = {
                if (recommendation.vip == true) {
                    SubscriptionActivity.startActivity(context as Activity)
                } else {
                    SerenityDetailActivity.startActivity(context as Activity, null)
                }
            },
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)) {

        Card(onClick = { }, modifier = modifier.padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outlineVariant)) {

            Box(modifier = modifier.size(height = 100.dp, width = 100.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(recommendation.image)
                        .crossfade(true)
                        .build(),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    placeholder = painterResource(id = R.drawable.ic_image_placeholder),
                    contentDescription = null,
                    modifier = modifier.size(height = 100.dp, width = 100.dp),
                    contentScale = ContentScale.Crop)

                if (recommendation.vip == true) {
                    Icon(
                        imageVector = Icons.Rounded.Diamond,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primaryContainer,
                        modifier = modifier.fillMaxSize().wrapContentSize(align = Alignment.TopStart).padding(4.dp)
                    )
                }
            }

        }

        Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = recommendation.title ?: "",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = recommendation.duration ?: "", style = MaterialTheme.typography.bodyMedium)
                Text(text = ".", style = MaterialTheme.typography.bodySmall)
                Text(text = recommendation.level ?: "", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)

    }
}

@Preview(showBackground = true)
@Composable
private fun YogaRecommendationCompose(recommendation: YogaRecommendation = YogaRecommendation(emptyList())) {

    val context = LocalContext.current

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
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
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    onClick = { RecommendationActivity.startActivity(context as Activity) },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null)
            )

            Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    onClick = { RecommendationActivity.startActivity(context as Activity) },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null)
            )

        }

        Column {
            recommendation.data?.forEach {
                RecommendationCard(recommendation = it ?: return)
            }
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