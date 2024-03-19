package com.bitvolper.yogazzz.presentation.serenitydetail.yogaexercise

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import kotlinx.coroutines.delay
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

@Composable
fun YogaCompletedScreen(modifier: Modifier = Modifier,
                        paddingValues: PaddingValues = PaddingValues(),
                        showSubscriptionSheet: () -> Unit = {  },
                        updateReports: () -> Unit = { }) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        updateReports()
        delay(3_000L)
        showSubscriptionSheet()
    }

    val party = listOf(Party(
        speed = 0f,
        maxSpeed = 30f,
        damping = 0.9f,
        spread = 360,
        colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
        emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
        position = Position.Relative(0.5, 0.3)
    ))

    Box {

        Column(modifier = modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = 0.dp,
                end = 0.dp,
                bottom = 16.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Spacer(modifier = modifier.weight(1f))

            Image(painter = painterResource(id = R.drawable.ic_completed),
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth()
                    .size(350.dp),
                contentScale = ContentScale.Crop
            )

            Text(text = "Congratulations",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold)

            Text(text = "You've completed the yoga!", style = MaterialTheme.typography.bodyMedium)

            Row(modifier = modifier
                .fillMaxWidth()
                .requiredHeight(100.dp)
                .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                Column(
                    modifier = modifier
                        .weight(1f)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Rounded.SelfImprovement, contentDescription = null)

                    Text(text = "11",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold)

                    Text(text = "movements", style = MaterialTheme.typography.bodyMedium)
                }

                Divider(modifier = modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .padding(vertical = 8.dp))

                Column(
                    modifier = modifier
                        .weight(1f)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Rounded.Schedule, contentDescription = null)

                    Text(text = "11",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold)

                    Text(text = "minutes", style = MaterialTheme.typography.bodyMedium)
                }

                Divider(modifier = modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .padding(vertical = 8.dp))

                Column(
                    modifier = modifier
                        .weight(1f)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Rounded.LocalFireDepartment, contentDescription = null)

                    Text(text = "200",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold)

                    Text(text = "kcal", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

                Divider(modifier = modifier.fillMaxWidth())

                Row(modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)) {


                    OutlinedButton(onClick = { (context as Activity).finish() }, modifier = modifier
                        .weight(1f)
                        .requiredHeight(50.dp)) {
                        Text(text = "Go to Homepage")
                    }


                    Button(
                        onClick = {   },
                        modifier = modifier
                            .weight(1f)
                            .requiredHeight(50.dp)) {

                        Text(text = "View Report")
                    }
                }
            }
        }

        KonfettiView(
            modifier = Modifier.fillMaxSize(),
            parties = party,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun YogaCompletedLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        YogaCompletedScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun YogaCompletedDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        YogaCompletedScreen()
    }
}