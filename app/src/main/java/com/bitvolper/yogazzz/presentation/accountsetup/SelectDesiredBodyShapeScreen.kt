package com.bitvolper.yogazzz.presentation.accountsetup

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FastForward
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.AccountSetupContinueComposable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectDesiredBodyShapeScreen(modifier: Modifier = Modifier,
                                 paddingValues: PaddingValues = PaddingValues(),
                                 onSkipClick: () -> Unit = {  },
                                 onContinueClick: (Int) -> Unit = { _ -> }) {

    var currentPosition by remember {
        mutableIntStateOf(0)
    }

    val outlineColor = MaterialTheme.colorScheme.outline

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "Your Desired Body Shape", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.SemiBold)

        Text(text = "What's your ideal aspiration?", style = MaterialTheme.typography.bodyLarge)


        Box(modifier = modifier
            .fillMaxWidth()
            .weight(1f) ) {

            Image(imageVector = Icons.Rounded.FastForward,
                contentDescription = null,
                modifier = modifier.fillMaxSize().wrapContentSize(align = Alignment.Center).size(250.dp),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
                contentScale = ContentScale.Crop)


            Row(modifier = modifier
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {


                Box(modifier = modifier.fillMaxHeight().weight(1f)) {

                    Canvas(modifier = Modifier.fillMaxSize()) {

                        val canvasSize = 350.dp.toPx()

                        drawOval(color = outlineColor, size = Size(width = size.width / 1.5f, height = 80.dp.toPx()), topLeft = Offset(x = size.width / 6f, y = canvasSize))
                    }

                    Image(painter = painterResource(id = R.drawable.ic_lean), contentDescription = null,
                        modifier = modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop)
                }


                Box(modifier = modifier.fillMaxHeight().weight(1f)) {

                    Canvas(modifier = Modifier.fillMaxSize()) {

                        val canvasSize = 350.dp.toPx()

                        drawOval(color = outlineColor, size = Size(width = size.width / 1.5f, height = 80.dp.toPx()), topLeft = Offset(x = size.width / 6f, y = canvasSize))
                    }

                    Image(painter = painterResource(id = R.drawable.ic_obese), contentDescription = null,
                        modifier = modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop)
                }
            }
        }

        Column(modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
            ForecastSlider(dates = listOf("Muscular", "Ideal", "Normal", "Fat", "Obeses"), currentPosition.toFloat()) {
                currentPosition = it
            }
        }

        AccountSetupContinueComposable(
            onSkipClick = onSkipClick,
            onContinueClick = { onContinueClick(currentPosition) }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SelectDesiredBodyShapeLightThemePreview() {
    YogaAppTheme {
        SelectDesiredBodyShapeScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SelectDesiredBodyShapeDarkThemePreview() {
    YogaAppTheme {
        SelectDesiredBodyShapeScreen()
    }
}