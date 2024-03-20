package com.bitvolper.yogazzz.presentation.accountsetup

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
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
fun SelectCurrentBodyShapeScreen(modifier: Modifier = Modifier,
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

        Text(text = "Your Current Body Shape", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.SemiBold)

        Text(text = "Help us understand your starting point.", style = MaterialTheme.typography.bodyLarge)


        Box(modifier = Modifier.weight(1f)) {

            Canvas(modifier = Modifier.fillMaxSize()) {

                val canvasSize = 350.dp.toPx()

                drawOval(color = outlineColor, size = Size(width = size.width / 3f, height = 80.dp.toPx()), topLeft = Offset(x = size.width / 3f, y = canvasSize))
            }

            Image(painter = painterResource(id = R.drawable.ic_obese),
                contentDescription = null,
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastSlider(dates: List<String>, value: Float, onValueChange: (Int) -> Unit) {
    val (sliderValue, setSliderValue) = remember { mutableFloatStateOf(value) }
    val drawPadding = with(LocalDensity.current) { 20.dp.toPx() }
    val textSize = with(LocalDensity.current) { 14.dp.toPx() }
    val lineHeightDp = 10.dp
    val lineHeightPx = with(LocalDensity.current) { lineHeightDp.toPx() }
    val canvasHeight = 80.dp
    val textPaint = android.graphics.Paint().apply {
        color = if (isSystemInDarkTheme()) 0xffffffff.toInt() else 0xffff47586B.toInt()
        textAlign = android.graphics.Paint.Align.CENTER

        this.textSize = textSize
    }
    Box(contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .height(canvasHeight)
                .fillMaxWidth()
                .padding(
                    top = canvasHeight
                        .div(2)
                        .minus(lineHeightDp.div(2))
                )
        ) {
            val yStart = 0f
            val distance = (size.width.minus(2 * drawPadding)).div(dates.size.minus(1))

            dates.forEachIndexed { index, date ->
                    this.drawContext.canvas.nativeCanvas.drawText(
                        dates[index],
                        drawPadding + index.times(distance),
                        size.height,
                        textPaint
                    )
            }
        }
        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = sliderValue,
            valueRange = 0f..dates.size.minus(1).toFloat(),
            steps = dates.size.minus(2),
            colors = customSliderColors(),
            onValueChange = {
                setSliderValue(it)
                onValueChange(it.toInt())
            },
            track = { sliderPositions ->
                SliderDefaults.Track(
                    modifier = Modifier
                        .scale(scaleX = 1f, scaleY = 3f)
                        .clip(RoundedCornerShape(100)),
                    sliderState = sliderPositions,
                    colors = SliderDefaults.colors(
                        activeTrackColor = Color.Transparent,
                        activeTickColor = MaterialTheme.colorScheme.outline,
                        inactiveTickColor = MaterialTheme.colorScheme.outline,
                        )
                )
            },
            thumb = { sliderPositions ->
                SliderDefaults.Thumb(
                    interactionSource = remember { MutableInteractionSource() },
                    thumbSize = DpSize(30.dp, 30.dp)
                    )
            },
            )
    }
}

@Composable
private fun customSliderColors(): SliderColors = SliderDefaults.colors(
    activeTickColor = Color.Transparent,
    inactiveTickColor = Color.Transparent
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SelectCurrentBodyShapeLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        SelectCurrentBodyShapeScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SelectCurrentBodyShapeDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        SelectCurrentBodyShapeScreen()
    }
}