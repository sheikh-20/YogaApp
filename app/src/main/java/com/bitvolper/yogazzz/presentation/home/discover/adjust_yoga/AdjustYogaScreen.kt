package com.bitvolper.yogazzz.presentation.home.discover.adjust_yoga

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.domain.model.AdjustYogaLevel
import com.bitvolper.yogazzz.presentation.home.RecommendationCard
import com.bitvolper.yogazzz.presentation.home.discover.AdjustYogaLevelCard
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.Resource

@Composable
fun AdjustYogaScreen(modifier: Modifier = Modifier,
                     paddingValues: PaddingValues = PaddingValues(),
                     adjustYogaLevelUIState: Resource<AdjustYogaLevel> = Resource.Loading) {

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center)
    ) {
        when (adjustYogaLevelUIState) {
            is Resource.Loading -> {
                CircularProgressIndicator()
            }

            is Resource.Failure -> {
                Text(text = adjustYogaLevelUIState.throwable.toString())
            }

            is Resource.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding(),
                            start = 16.dp, end = 16.dp
                        ), verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(adjustYogaLevelUIState.data.data?.size ?: return@LazyColumn) {
                            AdjustYogaLevelCard(
                                adjustYogaLevel = adjustYogaLevelUIState.data.data[it] ?: return@items
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AdjustYogaLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        AdjustYogaScreen()
    }
}


@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AdjustYogaDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        AdjustYogaScreen()
    }
}