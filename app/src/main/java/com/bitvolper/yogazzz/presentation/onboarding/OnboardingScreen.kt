package com.bitvolper.yogazzz.presentation.onboarding

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(modifier: Modifier = Modifier, onClick: () -> Unit = { }) {

    val coroutineScope = rememberCoroutineScope()
    val pager = rememberPagerState()

    Column(modifier = modifier
        .fillMaxSize()
        .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {

        OnboardingContent(modifier = modifier.weight(1f), state = pager)

        HorizontalPagerIndicator(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally),
            pagerState = pager,
            activeColor = Color.Red)

        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

            Divider(modifier = modifier.fillMaxWidth())

            Row(modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                if ((pager.currentPage != pager.pageCount.dec())) {
                    OutlinedButton(onClick = onClick, modifier = modifier
                        .weight(1f)
                        .requiredHeight(50.dp)) {
                        Text(text = "Skip")
                    }
                }

                Button(
                    onClick = {
                        coroutineScope.launch {
                            if (pager.currentPage < pager.pageCount - 1) {
                                pager.scrollToPage(pager.currentPage + 1)
                            } else {
                                onClick()
                            }
                        }
                    },
                    modifier = modifier
                        .weight(1f)
                        .requiredHeight(50.dp)) {

                    if (pager.currentPage == pager.pageCount - 1) {
                        Text(text = "Get started")
                    } else {
                        Text(text = "Next")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun OnboardingContent(modifier: Modifier = Modifier, state: PagerState = rememberPagerState()) {

    val datasource = listOf<HorizontalData>(
        HorizontalData(
            title = "Edit your photos simply in just one click",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        ),
        HorizontalData(
            title = "Unleash your creativity with AI toolbox",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        ),
        HorizontalData(
            title = "Enjoy all the benefits with pro subscriptions",
            description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        ),
    )

    HorizontalPager(modifier = modifier.padding(horizontal = 16.dp), count = datasource.size, state = state) { currentPage ->
        when (currentPage) {
            0 -> HorizontalContent(modifier = modifier, horizontalData = datasource[currentPage])
            1 -> HorizontalContent(modifier = modifier, horizontalData = datasource[currentPage])
            2 -> HorizontalContent(modifier = modifier, horizontalData = datasource[currentPage])
        }
    }
}

data class HorizontalData(val title: String, val description: String)

@Composable
private fun HorizontalContent(modifier: Modifier = Modifier, horizontalData: HorizontalData) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {

//        Image(painter = painterResource(id = R.drawable.doctor_strange), contentDescription = null,
//            modifier = modifier
//                .fillMaxHeight()
//                .drawWithCache {
//                    val gradient = Brush.verticalGradient(
//                        colors = listOf(Color.Transparent, Color.Black),
//                        startY = size.height / 3,
//                        endY = size.height
//                    )
//                    onDrawWithContent {
//                        drawContent()
//                        drawRect(gradient, blendMode = BlendMode.Multiply)
//                    }
//                },
//            contentScale = ContentScale.FillBounds)

        Spacer(modifier = modifier.weight(1f))

        Text(text = horizontalData.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center)

        Text(
            text = horizontalData.description,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OnboardingScreenLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        OnboardingScreen()
    }
}


@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun OnboardingScreenDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        OnboardingScreen()
    }
}