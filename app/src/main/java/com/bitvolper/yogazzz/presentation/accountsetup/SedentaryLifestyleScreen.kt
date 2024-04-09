package com.bitvolper.yogazzz.presentation.accountsetup

import android.content.res.Configuration
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.AccountSetupContinueComposable

@Composable
fun SedentaryLifestyleScreen(modifier: Modifier = Modifier,
                             paddingValues: PaddingValues = PaddingValues(),
                             onSkipClick: () -> Unit = { },
                             onContinueClick: (Boolean) -> Unit = { _ ->  }) {

    var isSedentaryLifestyle by remember { mutableStateOf(false) }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = stringResource(R.string.do_you_live_a_sedentary_lifestyle),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center)

        Text(text = stringResource(R.string.tell_us_about_your_routine),
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(horizontal = 16.dp))

        Image(painter = painterResource(id = R.drawable.ic_sedentary),
            contentDescription = null,
            modifier = modifier.requiredHeight(200.dp), contentScale = ContentScale.Crop)

        Row(modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)) {
            IconButton(onClick = { isSedentaryLifestyle = false }, modifier = modifier.size(120.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Rounded.Circle, contentDescription = null, tint = if (!isSedentaryLifestyle) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, modifier = modifier.fillMaxSize())
                    Text(text = stringResource(R.string.no), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }

            IconButton(onClick = { isSedentaryLifestyle = true }, modifier = modifier.size(120.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Rounded.Circle, contentDescription = null, tint = if (isSedentaryLifestyle) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, modifier = modifier.fillMaxSize())
                    Text(text = stringResource(R.string.yes), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }
        }

        Spacer(modifier = modifier.weight(1f))

        AccountSetupContinueComposable(
            onSkipClick = onSkipClick,
            onContinueClick = { onContinueClick(isSedentaryLifestyle) }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SedentaryLifestyleLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        SedentaryLifestyleScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SedentaryLifestyleDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        SedentaryLifestyleScreen()
    }
}