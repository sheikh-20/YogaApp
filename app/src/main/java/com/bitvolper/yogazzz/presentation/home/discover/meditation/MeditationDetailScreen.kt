package com.bitvolper.yogazzz.presentation.home.discover.meditation

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.Meditation
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import kotlinx.coroutines.launch

@Composable
fun MeditationDetailScreen(modifier: Modifier = Modifier,
                           paddingValues: PaddingValues = PaddingValues(),
                           onContinueScreen: () -> Unit = {  },
                           meditation: Meditation.Data = Meditation.Data()) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = 20.dp
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(modifier = modifier
            .fillMaxWidth()
            .requiredHeight(200.dp).padding(horizontal = 16.dp)) {

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(meditation.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.ic_image_placeholder),
                contentDescription = null,
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop)
        }

        Text(text = meditation.title ?: "",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.padding(horizontal = 16.dp))

        Text(text = meditation.description ?: "",
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier.padding(horizontal = 16.dp))

        Row(modifier = modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Card(shape = RoundedCornerShape(20)) {
                Text(text = meditation.duration ?: "", modifier = modifier.padding(8.dp), style = MaterialTheme.typography.bodySmall)
            }

            Card(shape = RoundedCornerShape(20)) {
                Text(text = meditation.category ?: "", modifier = modifier.padding(8.dp), style = MaterialTheme.typography.bodySmall)
            }
        }


        Spacer(modifier = modifier.weight(1f))

        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

            Divider(modifier = modifier.fillMaxWidth())

            Row(modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {


                Button(
                    onClick = onContinueScreen,
                    modifier = modifier
                        .weight(1f)
                        .requiredHeight(50.dp)) {

                        Text(text = "Continue")

                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MeditationDetailLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        MeditationDetailScreen()
    }
}


@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MeditationDetailDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        MeditationDetailScreen()
    }
}