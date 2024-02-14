package com.bitvolper.yogazzz.presentation.subscription

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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.bitvolper.yogazzz.utility.HorizontalPagerContent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SubscriptionScreen(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues()) {
    val items = listOf(
        HorizontalPagerContent(
            duration = "Monthly",
            title = "Asana Pro",
            price = "$9.99",
            description = listOf(
                "Full access to the entire library of yoga exercise and meditations.",
                "Personalized workout plans tailored to user goals.",
                "Advanced Customization options for personal profiles.",
                "Enhanced progress tracking and analytics.",
                "Exclusive early access to new features and content."
            ),
            validity = "month"
            ),
        HorizontalPagerContent(
            duration = "Yearly",
            title = "Asana Pro",
            price = "$99.99",
            description = listOf(
                "All benefits of the monthly subscription.",
                "Cost savings equivalent to two months free compared to the monthly plan.",
                "Priority customer support.",
                "Exclusive access to special events or challenges.",
                "20% discount on YogazzZ Merchandise."
            ),
            validity = "year"
        ),
    )

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val pager = rememberPagerState()

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = 16.dp
        )) {
        Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            TabRow(modifier = modifier.padding(horizontal = 16.dp), selectedTabIndex = pager.currentPage,  indicator = { tabPositions: List<TabPosition> -> Box {} }, divider = { }) {
                items.forEachIndexed { index, horizontalPagerContent ->
                    Tab(selected = pager.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pager.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(text = items[index].duration, color = if(pager.currentPage == index) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground)
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
                        ))
                }
            }

            HorizontalPager(
                count = items.size,
                state = pager,
                modifier = modifier
                    .fillMaxWidth()
            ) { index ->
                when (index) {
                    0 -> {
                        SubscriptionCard(horizontalPagerContent = items[index])
                    }

                    1 -> {
                        SubscriptionCard(horizontalPagerContent = items[index])
                    }
                }
            }

            Spacer(modifier = modifier.weight(1f))

            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

                Divider(modifier = modifier.fillMaxWidth())

                Button(
                    onClick = {  },
                    modifier = modifier.fillMaxWidth().requiredHeight(50.dp).padding(horizontal = 16.dp)) {

                    Text(text = "Continue - ${items[pager.currentPage].price}")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SubscriptionCard(modifier: Modifier = Modifier,
                             horizontalPagerContent: HorizontalPagerContent = HorizontalPagerContent("", "Asana Pro", "", emptyList(), "")
) {
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Text(text = horizontalPagerContent.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold)

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = horizontalPagerContent.price,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.SemiBold)

                Text(text = "/",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold)

                Text(text = horizontalPagerContent.validity,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold)

            }

            Divider()

            horizontalPagerContent.description.forEach {
                Row(modifier = modifier.fillMaxWidth().wrapContentWidth(align = Alignment.Start),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Rounded.Check, contentDescription = null)
                    Text(text = it, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SubscriptionLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        SubscriptionScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SubscriptionDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        SubscriptionScreen()
    }
}