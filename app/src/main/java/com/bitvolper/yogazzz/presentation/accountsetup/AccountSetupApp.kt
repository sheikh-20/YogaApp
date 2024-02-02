package com.bitvolper.yogazzz.presentation.accountsetup

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Spa
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.home.BottomNavigationScreens

@Composable
fun AccountSetupApp(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    Scaffold(
        topBar = { AccountSetupTopAppBar() }
    ) { paddingValues ->

        NavHost(
            navController = navController, startDestination = AccountSetupScreen.SelectGender.name) {

            composable(route = AccountSetupScreen.SelectGender.name) {
                SelectGenderScreen(paddingValues = paddingValues)
            }

            composable(route = AccountSetupScreen.SelectFocusArea.name) {
                SelectFocusAreaScreen(paddingValues = paddingValues)
            }

            composable(route = AccountSetupScreen.SelectYogaGoal.name) {
                YogaGoalScreen(paddingValues = paddingValues)
            }
        }
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountSetupTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth().requiredHeight(10.dp).padding(horizontal = 30.dp), progress = .1f, strokeCap = StrokeCap.Round)
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            Text(
                text = "1/14",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    )
}

enum class AccountSetupScreen {
    SelectGender, SelectFocusArea, SelectYogaGoal
}