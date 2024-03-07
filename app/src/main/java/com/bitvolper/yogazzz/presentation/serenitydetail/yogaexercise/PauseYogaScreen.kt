package com.bitvolper.yogazzz.presentation.serenitydetail.yogaexercise

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@Composable
fun PauseYogaScreen(modifier: Modifier = Modifier,
                    paddingValues: PaddingValues = PaddingValues()) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding() + 64.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Image(
            painter = painterResource(id = R.drawable.ic_yoga_exercise),
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .size(250.dp)
                .clip(RoundedCornerShape(10)),
            contentScale = ContentScale.Crop
        )

        Column(modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Warrior 1",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold
                )

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.Info, contentDescription = null)
                }
            }

            Text(
                text = "00:46",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Button(onClick = { /*TODO*/ }, modifier = modifier.fillMaxWidth()) {
            Text(text = "RESUME")
        }

        OutlinedButton(onClick = { /*TODO*/ }, modifier = modifier.fillMaxWidth()) {
            Text(text = "Restart")
        }
        OutlinedButton(onClick = { /*TODO*/ }, modifier = modifier.fillMaxWidth()) {
            Text(text = "Quit")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PauseYogaLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        PauseYogaScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PauseYogaDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        PauseYogaScreen()
    }
}
