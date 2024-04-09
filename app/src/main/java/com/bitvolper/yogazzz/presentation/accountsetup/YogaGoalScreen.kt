package com.bitvolper.yogazzz.presentation.accountsetup

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.AccountSetupContinueComposable
import com.bitvolper.yogazzz.utility.Body
import com.bitvolper.yogazzz.utility.Goal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YogaGoalScreen(modifier: Modifier = Modifier,
                   paddingValues: PaddingValues = PaddingValues(),
                   onSkipClick: () -> Unit = { },
                   onContinueClick: (List<Int>) -> Unit = { _ -> }
                   ) {

    val selectedIndexList = remember { mutableStateListOf(0) }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = stringResource(R.string.what_s_your_yoga_goal), style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.SemiBold)

        Text(text = stringResource(R.string.tell_us_what_you_aim_to_achieve_with_yogazzz), style = MaterialTheme.typography.bodyMedium)

        LazyColumn(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
            items(Goal.goals.size) {
                Card(shape = RoundedCornerShape(10),
                    border = BorderStroke(width = if (selectedIndexList.contains(it)) 2.dp else .5.dp, color =  if (selectedIndexList.contains(it)) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant),
                    onClick = { selectedIndexList.add(it) },
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {

                    Row(modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = Goal.goals[it].name),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            modifier = modifier.weight(1f)

                        )

                        if (selectedIndexList.contains(it)) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = modifier.weight(1f))

        AccountSetupContinueComposable(
            onSkipClick = onSkipClick, 
            onContinueClick = { onContinueClick(selectedIndexList) }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun YogaGoalLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        YogaGoalScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun YogaGoalDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        YogaGoalScreen()
    }
}