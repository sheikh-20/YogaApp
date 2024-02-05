package com.bitvolper.yogazzz.presentation.accountsetup

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.AccountSetupContinueComposable
import com.bitvolper.yogazzz.utility.Body
import com.bitvolper.yogazzz.utility.Gender

@Composable
fun SelectFocusAreaScreen(modifier: Modifier = Modifier,
                          paddingValues: PaddingValues = PaddingValues(),
                          onSkipClick: () -> Unit = {  },
                          onContinueClick: () -> Unit = {  }) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "What's your Focus Area?", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.SemiBold)

        Text(text = "Where would you like to channel your energy?", style = MaterialTheme.typography.bodyLarge)

        Row(modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { }, interactionSource = remember {
                MutableInteractionSource()
            }, indication = null)
            .padding(32.dp)
            .weight(1f), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {

            LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                items(Body.bodyParts.size) {
                    Card(modifier = modifier.requiredWidth(100.dp),
                        shape = RoundedCornerShape(10),
                        border = BorderStroke(width = .5.dp, color =  MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Text(
                            text = Body.bodyParts[it].part,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            modifier = modifier.padding(12.dp)
                        )
                    }
                }
            }

            ShowGenderImage()

        }

        AccountSetupContinueComposable(
            onSkipClick = onSkipClick,
            onContinueClick = onContinueClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ShowGenderImage(modifier: Modifier = Modifier, gender: Gender.GenderData = Gender.man, currentGender: Gender.GenderData? = null) {

    val context = LocalContext.current
    val primaryColor = MaterialTheme.colorScheme.primary
    val outlineColor = MaterialTheme.colorScheme.outline

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(500.dp)) {

            Canvas(modifier = Modifier
                .fillMaxSize()
                .padding()) {

                val canvasSize = 400.dp.toPx()

                drawOval(color = if (currentGender?.title == gender.title) primaryColor else outlineColor, size = Size(width = size.width, height = 80.dp.toPx()), topLeft = Offset(x = 0f, y = canvasSize))
            }

            Image(painter = painterResource(id = gender.image), contentDescription = null, modifier = Modifier.fillMaxSize())
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SelectFocusAreaLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        SelectFocusAreaScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SelectFocusAreaDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        SelectFocusAreaScreen()
    }
}