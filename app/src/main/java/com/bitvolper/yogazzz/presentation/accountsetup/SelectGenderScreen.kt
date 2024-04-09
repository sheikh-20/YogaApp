package com.bitvolper.yogazzz.presentation.accountsetup

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.AccountSetupContinueComposable
import com.bitvolper.yogazzz.utility.Gender
import com.google.accompanist.pager.ExperimentalPagerApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.res.stringResource
import com.bitvolper.yogazzz.domain.model.AccountInfo
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun SelectGenderScreen(modifier: Modifier = Modifier,
                       paddingValues: PaddingValues = PaddingValues(),
                       onSkipClick: () -> Unit = {  },
                       onContinueClick: (Int) -> Unit = {  }) {

    val items = listOf<Gender.GenderData>(Gender.man, Gender.woman)
    val pager = rememberPagerState(pageCount = { items.size })

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = stringResource(R.string.select_your_gender), style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.SemiBold)

        Text(text = stringResource(R.string.let_s_start_by_understanding_you), style = MaterialTheme.typography.bodyMedium)


        HorizontalPager(
            state = pager,
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(horizontal = 80.dp),
        ) { index ->

            val pageOffset =
                (pager.currentPage - index) + pager.currentPageOffsetFraction

            val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)

            Box(modifier = modifier
                .graphicsLayer {
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }
                .alpha(
                    scaleFactor.coerceIn(0f, 1f)
                )) {
                ShowGenderImage(modifier = modifier, gender = items[index], currentGender = items[pager.currentPage])
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

            Divider(modifier = modifier.fillMaxWidth())

            Row(modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                OutlinedButton(onClick = onSkipClick, modifier = modifier
                    .weight(1f)
                    .requiredHeight(50.dp),
                    border = BorderStroke(0.dp, Color.Transparent),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                    Text(text = stringResource(id = R.string.skip), color = MaterialTheme.colorScheme.onPrimaryContainer)
                }

                Button(
                    onClick = { onContinueClick(pager.currentPage) },
                    modifier = modifier
                        .weight(1f)
                        .requiredHeight(50.dp)) {

                    Text(text = stringResource(id = R.string.continues))
                }
            }
        }
    }
}

/**
 * Steps:
 * -> Show two object Male, Female
 * -> When click any object, show viewpager
 * -> User can scroll their gender
 *
 * */
@Preview(showBackground = true)
@Composable
private fun ShowGenderImage(modifier: Modifier = Modifier, gender: Gender.GenderData = Gender.man, currentGender: Gender.GenderData? = null) {

    val context = LocalContext.current
    val primaryColor = MaterialTheme.colorScheme.primary
    val outlineColor = MaterialTheme.colorScheme.outline

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(500.dp)) {

            Canvas(modifier = Modifier
                .fillMaxSize()
                .padding()) {

                val canvasSize = 400.dp.toPx()

                if (currentGender?.title == gender.title) {
                    drawCircle(color = primaryColor, radius = size.width / 3, center = Offset(x = size.width / 2, y = size.height / 4))
                    drawCircle(color = primaryColor, radius = 8.dp.toPx(), center = Offset(x = size.width / 8, y = size.height / 8))
                    drawCircle(color = primaryColor, radius = 7.dp.toPx(), center = Offset(x = size.width, y = size.height / 6))
                    drawCircle(color = primaryColor, radius = 4.dp.toPx(), center = Offset(x = size.width / 1.1f, y = size.height / 3.3f))
                    drawCircle(color = primaryColor, radius = 5.dp.toPx(), center = Offset(x = size.width / 8, y = size.height / 3))
                }

                drawOval(color = if (currentGender?.title == gender.title) primaryColor else outlineColor, size = Size(width = size.width, height = 80.dp.toPx()), topLeft = Offset(x = 0f, y = canvasSize))
            }

            Image(painter = painterResource(id = gender.image), contentDescription = null, modifier = Modifier.fillMaxSize())
        }

        Text(text = stringResource(id = gender.title), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SelectGenderLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        SelectGenderScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SelectGenderDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        SelectGenderScreen()
    }
}