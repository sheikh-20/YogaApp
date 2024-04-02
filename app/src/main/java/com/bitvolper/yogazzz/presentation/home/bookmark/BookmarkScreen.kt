package com.bitvolper.yogazzz.presentation.home.bookmark

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.AccountInfo
import com.bitvolper.yogazzz.domain.model.SerenityData
import com.bitvolper.yogazzz.domain.model.YogaExercise
import com.bitvolper.yogazzz.domain.model.YogaRecommendation
import com.bitvolper.yogazzz.presentation.home.RecommendationCard
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.Resource
import com.google.accompanist.pager.ExperimentalPagerApi
import timber.log.Timber

private const val TAG = "BookmarkScreen"
@Composable
fun BookmarkScreen(modifier: Modifier = Modifier,
                   paddingValues: PaddingValues = PaddingValues(),
                   onBookmark: () -> Unit = { },
                   bookmarkUIState: Resource<SerenityData> = Resource.Loading) {

    LaunchedEffect(key1 = Unit) {
        onBookmark()
    }

    Column(modifier = modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)) {
        when (bookmarkUIState) {
            is Resource.Loading -> {
                CircularProgressIndicator()
                Timber.tag(TAG).d("Loading")
            }
            is Resource.Failure -> {
                Text(text = bookmarkUIState.throwable.toString())
            }
            is Resource.Success -> {
                Timber.tag(TAG).d("Success")

                Column(modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)) {


                    if (bookmarkUIState.data.data?.isEmpty() == true) {
                        Column(modifier = modifier.fillMaxSize().wrapContentSize(align = Alignment.Center), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            Image(painter = painterResource(id = R.drawable.ic_empty),
                                contentDescription = null,
                                modifier = modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                                    .size(150.dp))

                            Text(text = "Empty",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.SemiBold)

                            Text(
                                text = "You did not bookmark any exercise",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                            )
                        }
                    } else {
                        LazyColumn(modifier = modifier.padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding(),
                            start = 16.dp, end = 16.dp
                        )) {
                            items(bookmarkUIState.data.data?.size ?: return@LazyColumn) {
                                BookmarkCard(
                                    yoga = bookmarkUIState.data.data[it] ?: return@items
                                )
                            }
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
private fun BookmarkCard(modifier: Modifier = Modifier,
                         yoga: SerenityData.Data = SerenityData.Data()) {

    val context = LocalContext.current

    Row(modifier = modifier.fillMaxWidth(),
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
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = yoga.duration ?: "", style = MaterialTheme.typography.bodyMedium)
                Text(text = ".", style = MaterialTheme.typography.bodySmall)
                Text(text = yoga.level ?: "", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BookmarkLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        BookmarkScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BookmarkDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        BookmarkScreen()
    }
}