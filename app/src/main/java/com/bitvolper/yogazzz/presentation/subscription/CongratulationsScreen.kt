package com.bitvolper.yogazzz.presentation.subscription

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@Composable
fun CongratulationsScreen(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues()) {

    Box(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = 16.dp
        )) {

        Column(modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
           , verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Icon(imageVector = Icons.Rounded.Check,
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(20.dp)
                    .size(30.dp))


            Text(text = "Congratulations",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center)

            Text(text = "You've completed the yoga!",
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center)

            Divider()

            Text(
                text = "Benefits Unlocked:",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Column(modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Rounded.Check,
                        contentDescription = null)

                    Text(text = "Full Access: Enjoy the complete library of yoga exercises and meditations")
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Rounded.Check,
                        contentDescription = null)

                    Text(text = "Personalized Plans: Tailor your workouts to achieve your unique goals.")
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Rounded.Check,
                        contentDescription = null)

                    Text(text = "Advanced Tracking: Dive deep into your progress with enhanced analytics")
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Rounded.Check,
                        contentDescription = null)

                    Text(text = "Exclusive Perks: Get early access to new features and special events")
                }
            }

            Divider()

            Text(text = "Thank you for investing in your well-being with YogazzZ", style = MaterialTheme.typography.bodyMedium)
        }


        Column(modifier = modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomCenter), verticalArrangement = Arrangement.spacedBy(20.dp)) {

            Divider(modifier = modifier.fillMaxWidth())

            Button(onClick = { },
                modifier = modifier
                    .shadow(
                        elevation = 4.dp,
                        ambientColor = MaterialTheme.colorScheme.outlineVariant,
                        spotColor = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(50)
                    )
                    .fillMaxWidth()
                    .requiredHeight(50.dp)
                    .padding(horizontal = 16.dp),) {

                    Text(text = "Ok", modifier = modifier.padding(4.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CongratulationsLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        CongratulationsScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CongratulationsDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        CongratulationsScreen()
    }
}