package com.bitvolper.yogazzz.presentation.mybody

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyBodyScreen(modifier: Modifier = Modifier,
                 paddingValues: PaddingValues = PaddingValues(),
                 height: Int = 0,
                 onHeightButtonClick: () -> Unit = { },
                 currentWeight: Double = 0.0,
                 onCurrentWeightButtonClick: () -> Unit = { },
                 targetWeight: Double = 0.0,
                 onTargetWeightButtonClick: () -> Unit = {  },
                 age: Long = 0L,
                 onAgeButtonClick: () -> Unit = { },
                 gender: Int = 0,
                 onGenderButtonClick: () -> Unit = { }) {


    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val pager = rememberPagerState()


    val title = listOf<String>("Kg, Cm", "Lbs, Ft")

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding()
        )) {
        Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(32.dp)) {
            TabRow(
                modifier = modifier.padding(horizontal = 16.dp),
                selectedTabIndex = pager.currentPage,
                indicator = { tabPositions: List<TabPosition> -> Box {} },
                divider = { }) {
                title.forEachIndexed { index, horizontalPagerContent ->
                    Tab(
                        selected = pager.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pager.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = title[index],
                                color = if (pager.currentPage == index) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
                            )
                        },
                        modifier = if (pager.currentPage == index)
                            Modifier
                                .shadow(
                                    elevation = 10.dp,
                                    spotColor = Color.Transparent,
                                    ambientColor = Color.Transparent
                                )
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(
                                        topStartPercent = if (index == 0) 20 else 0,
                                        bottomStartPercent = if (index == 0) 20 else 0,
                                        topEndPercent = if (index == 0) 0 else 20,
                                        bottomEndPercent = if (index == 0) 0 else 20,
                                    )
                                )
                        else Modifier.background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(
                                topStartPercent = if (index == 0) 20 else 0,
                                bottomStartPercent = if (index == 0) 20 else 0,
                                topEndPercent = if (index == 0) 0 else 20,
                                bottomEndPercent = if (index == 0) 0 else 20
                            )
                        )
                    )
                }
            }

            HorizontalPager(
                count = title.size,
                state = pager,
                modifier = modifier
                    .fillMaxWidth()
            ) { index ->
                when (index) {
                    0 -> {
                        Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            BodyPartCard(title = "Height", value = "$height cm", onClick = onHeightButtonClick)
                            BodyPartCard(title = "Weight", value = "$currentWeight kg", onClick = onCurrentWeightButtonClick)
                            BodyPartCard(title = "Target Weight", value = "$targetWeight kg", onClick = onTargetWeightButtonClick)
                            BodyPartCard(title = "Age", value = "$age", onClick = onAgeButtonClick)
                            BodyPartCard(title = "Gender", value = if (gender == 0) "Man" else "Woman", onClick = onGenderButtonClick)

                        }
                    }

                    1 -> {
                        Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            BodyPartCard(title = "Height", value = "$height cm")
                            BodyPartCard(title = "Weight", value = "$currentWeight kg")
                            BodyPartCard(title = "Target Weight", value = "$targetWeight kg")
                            BodyPartCard(title = "Age", value = "$age", onClick = onAgeButtonClick)
                            BodyPartCard(title = "Gender", value = if (gender == 0) "Man" else "Woman", onClick = onGenderButtonClick)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BodyPartCard(modifier: Modifier = Modifier, title: String = "Height", value: String = "185cm", onClick: () -> Unit = { }) {
    Card {
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = modifier.weight(1f))

            Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)

            IconButton(onClick = onClick) {
                Icon(imageVector = Icons.Rounded.Edit, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MyBodyLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        MyBodyScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MyBodyDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        MyBodyScreen()
    }
}