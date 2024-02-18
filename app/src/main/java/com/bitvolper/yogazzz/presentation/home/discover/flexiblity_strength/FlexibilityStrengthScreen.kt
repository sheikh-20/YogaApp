package com.bitvolper.yogazzz.presentation.home.discover.flexiblity_strength

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
import com.bitvolper.yogazzz.domain.model.FlexibilityStrength
import com.bitvolper.yogazzz.domain.model.PopularYoga
import com.bitvolper.yogazzz.presentation.home.discover.FlexibilityStrengthCard
import com.bitvolper.yogazzz.presentation.home.discover.PopularYogaCard
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.Resource

@Composable
fun FlexibilityStrengthScreen(modifier: Modifier = Modifier,
                              paddingValues: PaddingValues = PaddingValues(),
                              flexibilityStrengthUIState: Resource<FlexibilityStrength> = Resource.Loading) {

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center)
    ) {
        when (flexibilityStrengthUIState) {
            is Resource.Loading -> {
                CircularProgressIndicator()
            }

            is Resource.Failure -> {
                Text(text = flexibilityStrengthUIState.throwable.toString())
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
                        items(flexibilityStrengthUIState.data.data?.size ?: return@LazyColumn) {
                            FlexibilityStrengthCard(
                                flexibilityStrength = flexibilityStrengthUIState.data.data[it] ?: return@items
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
private fun FlexibilityStrengthLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        FlexibilityStrengthScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FlexibilityStrengthDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        FlexibilityStrengthScreen()
    }
}