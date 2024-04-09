package com.bitvolper.yogazzz.presentation.accountsetup

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.AccountSetupContinueComposable
import com.bitvolper.yogazzz.utility.ListPicker
import com.bitvolper.yogazzz.utility.toImmutableWrapper

@Composable
fun SetYogaPlanScreen(modifier: Modifier = Modifier,
                      paddingValues: PaddingValues = PaddingValues(),
                      onSkipClick: () -> Unit = { },
                      onContinueClick: (Int) -> Unit = { _ -> }) {

    var value by remember { mutableStateOf("4") }
    val values = remember { (1..7).map { it.toString() } }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = stringResource(R.string.set_your_weekly_yoga_plan),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
            )

        Text(text = stringResource(R.string.how_often_can_you_dedicate_time_to_yoga_each_week),
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center)

        com.bitvolper.yogazzz.presentation.accountsetup.utility.ListPicker(
            initialValue = value,
            list = values.toImmutableWrapper(),
            modifier = modifier
                .weight(1f)
                .requiredWidth(200.dp)
                .padding(vertical = 8.dp),
            onValueChange = {
                value = it
            },
            textStyle = TextStyle(fontSize = 32.sp),
            outOfBoundsPageCount = 4,
            verticalPadding = 8.dp,
        )


        AccountSetupContinueComposable(
            onSkipClick = onSkipClick,
            onContinueClick = { onContinueClick(value.toInt()) }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SetYogaPlanLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        SetYogaPlanScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SetYogaPlanDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        SetYogaPlanScreen()
    }
}