package com.bitvolper.yogazzz.presentation.accountsetup

import android.content.res.Configuration
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.AccountInfo
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.AccountSetupContinueComposable
import com.bitvolper.yogazzz.utility.Body
import com.bitvolper.yogazzz.utility.Gender

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectFocusAreaScreen(modifier: Modifier = Modifier,
                          paddingValues: PaddingValues = PaddingValues(),
                          onSkipClick: () -> Unit = {  },
                          onContinueClick: (Int) -> Unit = {  },
                          accountInfo: AccountInfo = AccountInfo()
) {

    var selectedIndex by remember { mutableIntStateOf(0) }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "What's your Focus Area?", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.SemiBold)

        Text(text = "Where would you like to channel your energy?", style = MaterialTheme.typography.bodyMedium)

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
                        border = BorderStroke(width = if (selectedIndex == it) 1.dp else .5.dp, color =  if (selectedIndex == it) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant),
                        onClick = { selectedIndex = it },
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
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

            if (accountInfo.gender != null) {
                if (accountInfo.gender == 0) {
                    ShowGenderImage(gender = R.drawable.ic_male)
                } else {
                    ShowGenderImage(gender = R.drawable.ic_female)
                }

            } else {
                ShowGenderImage(gender = R.drawable.ic_male)
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

            Divider(modifier = modifier.fillMaxWidth())

            Row(modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                OutlinedButton(onClick = onSkipClick, modifier = modifier
                    .weight(1f)
                    .requiredHeight(50.dp),
                    border = BorderStroke(0.dp, Color.Transparent),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                    Text(text = "Skip", color = MaterialTheme.colorScheme.onPrimaryContainer)
                }

                Button(
                    onClick = { onContinueClick(selectedIndex) },
                    modifier = modifier
                        .weight(1f)
                        .requiredHeight(50.dp)) {

                    Text(text = "Continue")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShowGenderImage(modifier: Modifier = Modifier, @DrawableRes gender: Int = R.drawable.ic_male) {

    val outlineColor = MaterialTheme.colorScheme.outline

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(500.dp)) {

            Canvas(modifier = Modifier
                .fillMaxSize()
                .padding()) {

                val canvasSize = 400.dp.toPx()

                drawOval(color = outlineColor, size = Size(width = size.width, height = 80.dp.toPx()), topLeft = Offset(x = 0f, y = canvasSize))
            }

            Image(painter = painterResource(id = gender), contentDescription = null, modifier = Modifier.fillMaxSize())
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