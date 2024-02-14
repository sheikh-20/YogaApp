package com.bitvolper.yogazzz.presentation.billing

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.HorizontalPagerContent

@Composable
fun BillingSubscriptionScreen(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues()) {

    val content = HorizontalPagerContent(
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
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = 16.dp, end = 16.dp, bottom = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SubscriptionCard(horizontalPagerContent = content)

        Text(text = "Your subscription will expire on Dec 22, 2024.\nRenew or cancel your subscription here.",
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth().wrapContentWidth(align = Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium)
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
                Row(modifier = modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.Start),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Rounded.Check, contentDescription = null)
                    Text(text = it, style = MaterialTheme.typography.bodyLarge)
                }
            }

            Divider()

            Text(text = "Your current plan",
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier.padding(8.dp))
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BillingSubscriptionLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        BillingSubscriptionScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BillingSubscriptionDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        BillingSubscriptionScreen()
    }
}