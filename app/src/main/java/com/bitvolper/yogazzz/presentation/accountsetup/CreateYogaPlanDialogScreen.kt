package com.bitvolper.yogazzz.presentation.accountsetup

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.AccountSetupContinueComposable
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CreateYogaPlanDialogScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = 16.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "Creating Personalized Yoga Plan For You", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.SemiBold)

        Text(text = "Please wait", style = MaterialTheme.typography.bodyLarge)

        ComposeCircularProgressBar(
            percentage = 0.80f,
            fillColor = Color(android.graphics.Color.parseColor("#4DB6AC")),
            backgroundColor = Color(android.graphics.Color.parseColor("#90A4AE")),
            strokeWidth = 15.dp,
            modifier = modifier.weight(1f)
        )

        Text(text = "This will just take a moment. Get ready to transform your yoga journey.", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun ComposeCircularProgressBar(
    modifier: Modifier = Modifier,
    percentage: Float,
    fillColor: Color,
    backgroundColor: Color,
    strokeWidth: Dp
) {

    val unfilledColor = MaterialTheme.colorScheme.tertiaryContainer

    Box(modifier = modifier.size(200.dp), contentAlignment = Alignment.Center) {

        Canvas(
            modifier = modifier
                .size(200.dp)
                .padding(10.dp)
        ) {

            drawArc(
                color = unfilledColor,
                0f,
                360f,
                false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
                size = Size(size.width, size.height)
            )

            drawArc(
                color = fillColor,
                270f,
                360f,
                false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
                size = Size(size.width, size.height)
            )
        }

        Text(text = "75%", style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold)

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CreateYogaPlanDialogLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        CreateYogaPlanDialogScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CreateYogaPlanDialogDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        CreateYogaPlanDialogScreen()
    }
}