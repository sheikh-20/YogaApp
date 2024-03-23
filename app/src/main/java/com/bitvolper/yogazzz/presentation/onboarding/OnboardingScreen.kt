package com.bitvolper.yogazzz.presentation.onboarding

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
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
        .padding(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {

        OnboardingContent(modifier = modifier.weight(1f), state = pager)

        HorizontalPagerIndicator(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally),
            pagerState = pager,
            activeColor = MaterialTheme.colorScheme.primary)

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
                        .requiredHeight(50.dp),
                        border = BorderStroke(0.dp, Color.Transparent),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                        Text(text = "Skip", color = MaterialTheme.colorScheme.onPrimaryContainer)
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
            image = R.drawable.ic_home_screen,
            title = "YogazzZ - Your Yoga Journey Starts Here",
            description = "Get ready to embark on a transformative yoga journey with YogazzZ. Discover a wide range of yogas, tailored to your goals."
        ),
        HorizontalData(
            image = R.drawable.ic_home_screen2,
            title = "Tailored Exercise Plan for Your Needs",
            description = "YogazzZ personalizes yoga just for you. Whether you're a beginner or a yoga enthusiast, our app adapt to your needs"
        ),
        HorizontalData(
            image = R.drawable.ic_home_screen3,
            title = "Stay Informed About Your Yoga Progress",
            description = "Stay motivated and track your progress effortlessly. Start your yoga journey today and achieve the results you've always wanted"
        ),
    )

    HorizontalPager(modifier = modifier, count = datasource.size, state = state) { currentPage ->
        when (currentPage) {
            0 -> HorizontalContent(modifier = modifier, horizontalData = datasource[currentPage])
            1 -> HorizontalContent(modifier = modifier, horizontalData = datasource[currentPage])
            2 -> HorizontalContent(modifier = modifier, horizontalData = datasource[currentPage])
        }
    }
}

data class HorizontalData(@DrawableRes val image: Int, val title: String, val description: String)


private val cropShape = GenericShape { size: Size, layoutDirection: LayoutDirection ->
    addRect(Rect(0f, 0f, size.width, size.height * .8f))
}

@Composable
private fun HorizontalContent(modifier: Modifier = Modifier, horizontalData: HorizontalData) {

    val color = MaterialTheme.colorScheme.background

    Box(modifier = modifier.background(color = Color(0xFF655de6))) {


            Image(painter = painterResource(id = horizontalData.image), contentDescription = null,
                modifier = modifier
                    .fillMaxSize()
                    .offset(x = 0.dp, y = 50.dp)
                    .clip(cropShape)
                    .drawWithCache {
                        val gradient = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = size.height / 2f,
                            endY = size.height
                        )
                        onDrawWithContent {
                            drawContent()
                            drawRect(gradient, blendMode = BlendMode.Multiply)
                        }
                    }
                    .padding(horizontal = 16.dp),
                )



        Column(
            modifier = modifier.fillMaxWidth()) {

            Spacer(modifier = modifier.weight(1f))

            Column(modifier = Modifier.drawBehind {
                val canvasWidth = size.width
                val canvasHeight = size.height

                val filledPath = Path()
                filledPath.lineTo(x = 0f, y = 0f)
                filledPath.lineTo(x = 0f, y = size.height)
                filledPath.lineTo(x = size.width, y = size.height)
                filledPath.lineTo(x = size.width, y = 0f)
                filledPath.cubicTo(
                    x1 = size.width / 1.5f, y1 = size.height / 4f,
                    x2 = size.width / 2.5f, y2 = size.height / 4f,
                    x3 = 0f, y3 = 0f)
                filledPath.close()

                drawPath(
                    path = filledPath,
                    color = color,
                    style = Fill
                )
            }, verticalArrangement = Arrangement.spacedBy(16.dp)) {

                Spacer(modifier = Modifier.requiredHeight(50.dp))

                Text(text = horizontalData.title,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center)

                Text(
                    text = horizontalData.description,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
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