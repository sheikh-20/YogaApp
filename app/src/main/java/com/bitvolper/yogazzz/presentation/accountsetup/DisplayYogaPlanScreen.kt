package com.bitvolper.yogazzz.presentation.accountsetup

import android.app.Activity
import android.content.res.Configuration
import android.text.format.DateFormat
import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.YogaRecommendation
import com.bitvolper.yogazzz.presentation.home.HomeActivity
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.AccountSetupContinueComposable
import com.google.accompanist.pager.ExperimentalPagerApi
import timber.log.Timber
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayYogaPlanScreen(modifier: Modifier = Modifier,
                          paddingValues: PaddingValues = PaddingValues(),
                          onContinueClick: () -> Unit = { }) {

    val context = LocalContext.current

    val dayTheme = if (isSystemInDarkTheme()) R.style.CustomDayDark else R.style.CustomDay
    val weekTheme = if (isSystemInDarkTheme()) R.style.CustomWeekDark else R.style.CustomWeek
    val customTheme = if (isSystemInDarkTheme()) R.style.CustomCalendarDark else R.style.CustomCalendar

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "Your Yoga Plan is Ready!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center)

        Text(text = "Your personalized yoga plan has been generated based on your goals & physics",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center)

        YogaCard()

        Card(border = BorderStroke(width = .5.dp, color = MaterialTheme.colorScheme.outlineVariant), colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
            AndroidView(
                factory = {
                    CalendarView(ContextThemeWrapper(it, customTheme)).apply {
                    }
                },
                update = {
                    it.apply {
                        dateTextAppearance = dayTheme
                        weekDayTextAppearance = weekTheme
                    }
                })
        }

        Spacer(modifier = modifier.weight(1f))

        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

            Divider(modifier = modifier.fillMaxWidth())

            Row(modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                OutlinedButton(onClick = onContinueClick, modifier = modifier
                    .weight(1f)
                    .requiredHeight(50.dp)) {
                    Text(text = "Go to Homepage")
                }

                Button(
                    onClick = onContinueClick,
                    modifier = modifier
                        .weight(1f)
                        .requiredHeight(50.dp)) {

                    Text(text = "Get Started")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
private fun YogaCard(modifier: Modifier = Modifier,
                       recommendation: YogaRecommendation.Data = YogaRecommendation.Data(title = "Yoga Exercise", duration = "10 mins", level = "Beginner")) {

    val context = LocalContext.current

    Row(modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)) {

        Card(onClick = { }, modifier = modifier.padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outlineVariant)) {

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(recommendation.image)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.ic_image_placeholder),
                contentDescription = null,
                modifier = modifier
                    .size(height = 100.dp, width = 100.dp),
                contentScale = ContentScale.Crop)

        }

        Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = recommendation.title ?: "",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = recommendation.duration ?: "", style = MaterialTheme.typography.bodyMedium)
                Text(text = ".", style = MaterialTheme.typography.bodySmall)
                Text(text = recommendation.level ?: "", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DisplayYogaPlanLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        DisplayYogaPlanScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DisplayYogaPlanDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        DisplayYogaPlanScreen()
    }
}