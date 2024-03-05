package com.bitvolper.yogazzz.presentation.accountsetup

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.AccountSetupContinueComposable
import com.bitvolper.yogazzz.utility.Experience

@Composable
fun ExperienceLevelScreen(modifier: Modifier = Modifier,
                          paddingValues: PaddingValues = PaddingValues(),
                          onSkipClick: () -> Unit = { },
                          onContinueClick: (Int) -> Unit = { _ -> }) {

    var selectedExperienceLevel by remember { mutableIntStateOf(0) }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "Have You Experienced Yoga Before?",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center)

        Text(text = "Share your yoga background with us",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(horizontal = 16.dp))

        Spacer(modifier = modifier.requiredHeight(10.dp))

        LazyColumn(modifier = modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(Experience.levels.size) {
                ExperienceCardComposable(
                    experienceLevel = Experience.levels[it],
                    selectedExperienceLevel = selectedExperienceLevel == it,
                    onCardClick = { selectedExperienceLevel = it })
            }
        }

        Spacer(modifier = modifier.weight(1f))

        AccountSetupContinueComposable(
            onSkipClick = onSkipClick,
            onContinueClick = { onContinueClick(selectedExperienceLevel) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ExperienceCardComposable(experienceLevel: Experience.ExperienceLevel = Experience.levels.first(),
                                     selectedExperienceLevel: Boolean = false,
                                     onCardClick: () -> Unit = { }) {
    Card(
        onClick = onCardClick,
        border = BorderStroke(width = if (selectedExperienceLevel) 2.dp else .5.dp, color =  if (selectedExperienceLevel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            Image(imageVector = experienceLevel.imageVector,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary))

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = experienceLevel.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                Text(text = experienceLevel.description, style = MaterialTheme.typography.bodyMedium)
            }

            if (selectedExperienceLevel) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ExperienceLevelLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        ExperienceLevelScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ExperienceLevelDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        ExperienceLevelScreen()
    }
}