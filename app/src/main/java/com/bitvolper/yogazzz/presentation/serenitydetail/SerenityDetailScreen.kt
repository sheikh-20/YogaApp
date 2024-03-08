package com.bitvolper.yogazzz.presentation.serenitydetail

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.VideoFrameDecoder
import coil.request.ImageRequest
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.SerenityData
import com.bitvolper.yogazzz.domain.model.YogaExercise
import com.bitvolper.yogazzz.presentation.serenitydetail.yogaexercise.YogaExerciseActivity
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.Resource
import com.google.accompanist.pager.ExperimentalPagerApi

@Composable
fun SerenityDetailScreen(modifier: Modifier = Modifier,
                         paddingValues: PaddingValues = PaddingValues(),
                         yogaExerciseUIState: Resource<SerenityData> = Resource.Loading,
                       ) {

    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            add(VideoFrameDecoder.Factory())
        }
        .build()

    when (yogaExerciseUIState) {
        is Resource.Loading -> {
            CircularProgressIndicator(modifier = modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center))
        }

        is Resource.Failure -> {
            Text(text = yogaExerciseUIState.throwable.toString(),
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.Center))
        }

        is Resource.Success -> {
            Box {
                Column(modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)) {

                    AsyncImage(
                        model = yogaExerciseUIState.data.data?.first()?.pose?.first()?.file,
                        imageLoader = imageLoader,
                        error = painterResource(id = R.drawable.ic_broken_image),
                        placeholder = painterResource(id = R.drawable.ic_image_placeholder),
                        contentDescription = null,
                        modifier = modifier.requiredHeight(250.dp),
                        contentScale = ContentScale.Crop)

                    Text(text = yogaExerciseUIState.data.data?.first()?.title ?: "",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp))

                    Text(text = yogaExerciseUIState.data.data?.first()?.description ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp))

                    Row(modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                        Card(
                            modifier = modifier.weight(1f),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)) {
                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(imageVector = Icons.Rounded.SelfImprovement, contentDescription = null)

                                Text(text = yogaExerciseUIState.data.data?.first()?.moments ?: "",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold)

                                Text(text = "movements", style = MaterialTheme.typography.bodyMedium)
                            }
                        }

                        Card( modifier = modifier.weight(1f),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)) {
                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(imageVector = Icons.Rounded.Schedule, contentDescription = null)

                                Text(text = yogaExerciseUIState.data.data?.first()?.duration ?: "",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold)

                                Text(text = "minutes", style = MaterialTheme.typography.bodyMedium)
                            }
                        }

                        Card(
                            modifier = modifier.weight(1f),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)) {
                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(imageVector = Icons.Rounded.LocalFireDepartment, contentDescription = null)

                                Text(text = yogaExerciseUIState.data.data?.first()?.kcal ?: "",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold)

                                Text(text = "kcal", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }

                    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(yogaExerciseUIState.data.data?.first()?.pose?.size ?: return@LazyColumn) {
                            ExerciseCard(yoga = yogaExerciseUIState.data.data.first()?.pose?.get(it) ?: return@items)
                        }
                    }
                }

                Column(modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.BottomCenter)
                    .padding(bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {

                    Divider(modifier = modifier.fillMaxWidth())

                    Row(modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                        Button(
                            onClick = {  YogaExerciseActivity.startActivity(context as Activity) },
                            modifier = modifier
                                .weight(1f)
                                .requiredHeight(50.dp)) {

                            Text(text = "START")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
private fun ExerciseCard(modifier: Modifier = Modifier,
                         yoga: SerenityData.Data.Pose = SerenityData.Data.Pose()) {

    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            add(VideoFrameDecoder.Factory())
        }
        .build()

    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        
        Icon(imageVector = Icons.Outlined.Apps, contentDescription = null)

        Card(onClick = { }, modifier = modifier.padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outlineVariant)) {

            AsyncImage(
                model = yoga.file,
                imageLoader = imageLoader,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.ic_image_placeholder),
                contentDescription = null,
                modifier = modifier
                    .size(height = 80.dp, width = 80.dp),
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
                Text(text = yoga.duration ?: "", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SerenityDetailLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        SerenityDetailScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SerenityDetailDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        SerenityDetailScreen()
    }
}