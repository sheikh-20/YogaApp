package com.bitvolper.yogazzz.presentation.accountsetup

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.AccountSetupContinueComposable
import com.bitvolper.yogazzz.utility.toImmutableWrapper

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CollectHeightScreen(modifier: Modifier = Modifier,
                        paddingValues: PaddingValues = PaddingValues(),
                        onSkipClick: () -> Unit = {  },
                        onContinueClick: (Int, Int) -> Unit = { _, _ -> }) {

    var cmValue by remember { mutableStateOf("150") }
    val cmValues = remember { (50..300).map { it.toString() } }

    var ftValue by remember { mutableStateOf("6") }
    val ftValues = remember { (1..9).map { it.toString() } }

    val color = MaterialTheme.colorScheme.primary
    var currentMeasure by remember { mutableIntStateOf(Measure.CM.id) }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = stringResource(R.string.what_s_your_height), style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.SemiBold)

        Text(text = stringResource(R.string.how_tall_are_you), style = MaterialTheme.typography.bodyLarge)


        Row(modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {


            Chip(onClick = { currentMeasure = Measure.CM.id },
                colors = ChipDefaults.chipColors(backgroundColor = if (currentMeasure == Measure.CM.id) color else Color.Transparent),
                border = BorderStroke(width = 1.dp, color = if (currentMeasure == Measure.CM.id) Color.Transparent else MaterialTheme.colorScheme.outline)
            ) {
                Text(text = "cm",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier.padding(10.dp),
                    color =  if (currentMeasure == Measure.CM.id) Color.White else Color.Unspecified)
            }

            Chip(onClick = { currentMeasure = Measure.FT.id },
                colors = ChipDefaults.chipColors(backgroundColor = if (currentMeasure == Measure.FT.id) color else Color.Transparent),
                border = BorderStroke(width = 1.dp, color = if (currentMeasure == Measure.FT.id) Color.Transparent else MaterialTheme.colorScheme.outline)
            ) {
                Text(text = "ft",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier.padding(10.dp),
                    color = if (currentMeasure == Measure.FT.id) Color.White else Color.Unspecified)
            }
        }

        Spacer(modifier = modifier.weight(1f))


        if (currentMeasure == Measure.CM.id) {
            com.bitvolper.yogazzz.presentation.accountsetup.utility.ListPicker(
                initialValue = cmValue,
                list = cmValues.toImmutableWrapper(),
                modifier = modifier
                    .requiredWidth(200.dp)
                    .padding(vertical = 8.dp),
                onValueChange = {
                    cmValue = it
                },
                textStyle = TextStyle(fontSize = 32.sp),
                outOfBoundsPageCount = 3,
                verticalPadding = 8.dp,
            )
        } else {
            com.bitvolper.yogazzz.presentation.accountsetup.utility.ListPicker(
                initialValue = ftValue,
                list = ftValues.toImmutableWrapper(),
                modifier = modifier
                    .requiredWidth(200.dp)
                    .padding(vertical = 8.dp),
                onValueChange = {
                    ftValue = it
                },
                textStyle = TextStyle(fontSize = 32.sp),
                outOfBoundsPageCount = 3,
                verticalPadding = 8.dp,
            )
        }

        Spacer(modifier = modifier.weight(1f))

        AccountSetupContinueComposable(
            onSkipClick = onSkipClick,
            onContinueClick = {
                if (currentMeasure == Measure.CM.id) {
                    onContinueClick(cmValue.toInt(), currentMeasure)
                } else {
                    onContinueClick(ftValue.toInt(), currentMeasure)
                }
            }
        )
    }
}

enum class Measure(val id: Int) {
    CM(0), FT(1)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CollectHeightLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        CollectHeightScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CollectHeightDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        CollectHeightScreen()
    }
}