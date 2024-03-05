package com.bitvolper.yogazzz.presentation.accountsetup

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.AccountSetupContinueComposable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectPlankDurationScreen(modifier: Modifier = Modifier,
                              paddingValues: PaddingValues = PaddingValues(),
                              onSkipClick: () -> Unit = {  },
                              onContinueClick: () -> Unit = { }) {

    var currentPosition by remember {
        mutableIntStateOf(0)
    }


    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "How Long Can You Hold a Plank?",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center)

        Text(text = "Show us your core endurance",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(horizontal = 16.dp))


        Spacer(modifier = modifier.weight(1f))

        Image(painter = painterResource(id = R.drawable.ic_plank),
            contentDescription = null,
            modifier = modifier.requiredHeight(200.dp), contentScale = ContentScale.Crop)

        Spacer(modifier = modifier.weight(1f))

        Column(modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
            ForecastSlider(dates = listOf("< 20s", "20 - 60s", "> 60s"), currentPosition.toFloat()) {
                currentPosition = it
            }
        }

        AccountSetupContinueComposable(
            onSkipClick = onSkipClick,
            onContinueClick = onContinueClick
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SelectPlanDurationLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        SelectPlankDurationScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SelectPlanDurationDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        SelectPlankDurationScreen()
    }
}