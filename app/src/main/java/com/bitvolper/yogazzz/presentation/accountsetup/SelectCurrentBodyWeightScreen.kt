package com.bitvolper.yogazzz.presentation.accountsetup

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.AccountSetupContinueComposable
import com.bitvolper.yogazzz.utility.toImmutableWrapper

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectCurrentBodyWeightScreen(modifier: Modifier = Modifier,
                                  paddingValues: PaddingValues = PaddingValues(),
                                  onSkipClick: () -> Unit = {  },
                                  onContinueClick: (Double, Int) -> Unit = { _, _ ->  }) {

    var kgValue by remember { mutableStateOf("50") }
    val kgValues = remember { (10..200).map { it.toString() } }

    var lbsValue by remember { mutableStateOf("100") }
    val lbsValues = remember { (50..400).map { it.toString() } }

    val color = MaterialTheme.colorScheme.primary
    var currentMeasure by remember { mutableIntStateOf(MeasureWeight.Kg.id) }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "Your Current Weight", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.SemiBold)

        Text(text = "Share your current weight.", style = MaterialTheme.typography.bodyLarge)

        Row(modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {


            Chip(onClick = { currentMeasure = MeasureWeight.Kg.id },
                colors = ChipDefaults.chipColors(backgroundColor = if (currentMeasure == MeasureWeight.Kg.id) color else Color.Transparent),
                border = BorderStroke(width = 1.dp, color = if (currentMeasure == MeasureWeight.Kg.id) Color.Transparent else MaterialTheme.colorScheme.outline)
            ) {
                Text(text = "kg",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier.padding(10.dp),
                    color =  if (currentMeasure == MeasureWeight.Kg.id) Color.White else Color.Unspecified)
            }

            Chip(onClick = { currentMeasure = MeasureWeight.Lbs.id },
                colors = ChipDefaults.chipColors(backgroundColor = if (currentMeasure == MeasureWeight.Lbs.id) color else Color.Transparent),
                border = BorderStroke(width = 1.dp, color = if (currentMeasure == MeasureWeight.Lbs.id) Color.Transparent else MaterialTheme.colorScheme.outline)
            ) {
                Text(text = "lbs",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier.padding(10.dp),
                    color = if (currentMeasure == MeasureWeight.Lbs.id) Color.White else Color.Unspecified)
            }
        }

        if (currentMeasure == MeasureWeight.Kg.id) {
            com.bitvolper.yogazzz.presentation.accountsetup.utility.ListPicker(
                initialValue = kgValue,
                list = kgValues.toImmutableWrapper(),
                modifier = modifier
                    .weight(1f)
                    .requiredWidth(200.dp)
                    .padding(vertical = 8.dp),
                onValueChange = {
                    kgValue = it
                },
                textStyle = TextStyle(fontSize = 32.sp),
                outOfBoundsPageCount = 4,
                verticalPadding = 8.dp,
            )
        } else {
            com.bitvolper.yogazzz.presentation.accountsetup.utility.ListPicker(
                initialValue = lbsValue,
                list = lbsValues.toImmutableWrapper(),
                modifier = modifier
                    .weight(1f)
                    .requiredWidth(200.dp)
                    .padding(vertical = 8.dp),
                onValueChange = {
                    lbsValue = it
                },
                textStyle = TextStyle(fontSize = 32.sp),
                outOfBoundsPageCount = 4,
                verticalPadding = 8.dp,
            )
        }

        AccountSetupContinueComposable(
            onSkipClick = onSkipClick,
            onContinueClick = {
                if (currentMeasure == MeasureWeight.Kg.id) {
                    onContinueClick(kgValue.toDouble(), currentMeasure)
                } else {
                    onContinueClick(kgValue.toDouble(), currentMeasure)
                }
            }
        )
    }
}

enum class MeasureWeight(val id: Int) {
    Kg(0), Lbs(1)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SelectCurrentBodyWeightLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        SelectCurrentBodyWeightScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SelectCurrentBodyWeightDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        SelectCurrentBodyWeightScreen()
    }
}