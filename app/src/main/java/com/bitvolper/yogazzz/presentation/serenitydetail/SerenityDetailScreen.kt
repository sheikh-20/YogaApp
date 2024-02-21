package com.bitvolper.yogazzz.presentation.serenitydetail

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@Composable
fun SerenityDetailScreen(modifier: Modifier = Modifier,
                         paddingValues: PaddingValues = PaddingValues()) {

    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Image(painter = painterResource(id = R.drawable.ic_home_screen),
            contentDescription = null,
            modifier = modifier.requiredHeight(200.dp),
            contentScale = ContentScale.Crop
            )

        Text(text = "Serenity Flow: Yoga For Beginners",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp))

        Text(text = "A serene beginner's journey, blending gentle poses and mindul breathing for transquility and balance with Serenity Flow.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp))

        Row(modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {

            Card(
                modifier = modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)) {
                Column(
                    modifier = modifier.fillMaxWidth().padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Rounded.SelfImprovement, contentDescription = null)

                    Text(text = "11",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold)

                    Text(text = "movements", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Card( modifier = modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)) {
                Column(
                    modifier = modifier.fillMaxWidth().padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Rounded.Schedule, contentDescription = null)

                    Text(text = "11",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold)

                    Text(text = "minutes", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Card(
                modifier = modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)) {
                Column(
                    modifier = modifier.fillMaxWidth().padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Rounded.LocalFireDepartment, contentDescription = null)

                    Text(text = "200",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold)

                    Text(text = "kcal", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SerenityDetailLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        SerenityDetailScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SerenityDetailDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        SerenityDetailScreen()
    }
}