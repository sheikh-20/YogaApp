package com.bitvolper.yogazzz.presentation.accountsetup

import android.app.Activity
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.home.BottomNavigationScreens
import com.bitvolper.yogazzz.presentation.home.HomeActivity
import com.bitvolper.yogazzz.presentation.home.discover.meditation.Meditation
import com.bitvolper.yogazzz.presentation.viewmodel.AccountSetupUIState
import com.bitvolper.yogazzz.presentation.viewmodel.AccountSetupViewModel
import com.bitvolper.yogazzz.utility.ACCOUNT_SETUP_MAX_SCREEN

@Composable
fun AccountSetupApp(modifier: Modifier = Modifier,
                    navController: NavHostController = rememberNavController(),
                    accountSetupViewModel: AccountSetupViewModel = viewModel()) {

    val context = LocalContext.current

    val uiState by accountSetupViewModel.uiState.collectAsState()
    val accountInfoUIState by accountSetupViewModel.accountInfoUIState.collectAsState()

    Scaffold(
        topBar = {
            AccountSetupTopAppBar(
                navController = navController,
                navigateUp = {
                    accountSetupViewModel.degradeCurrentScreen {
                        navController.navigateUp()
                    }
                },
                uiState = uiState) }
    ) { paddingValues ->

        NavHost(
            navController = navController, startDestination = AccountSetupScreen.SelectGender.name) {

            composable(route = AccountSetupScreen.SelectGender.name) {
                SelectGenderScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        navController.navigate(AccountSetupScreen.SelectFocusArea.name)
                    },
                    onContinueClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        accountSetupViewModel.updateGender(it)
                        navController.navigate(AccountSetupScreen.SelectFocusArea.name)
                    })
            }

            composable(route = AccountSetupScreen.SelectFocusArea.name) {
                SelectFocusAreaScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        navController.navigate(AccountSetupScreen.SelectYogaGoal.name)
                    },
                    onContinueClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        accountSetupViewModel.updateFocusArea(it)
                        navController.navigate(AccountSetupScreen.SelectYogaGoal.name)
                    },
                    accountInfo = accountInfoUIState
                    )
            }

            composable(route = AccountSetupScreen.SelectYogaGoal.name) {
                YogaGoalScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        navController.navigate(AccountSetupScreen.CurrentBodyShape.name)
                    },
                    onContinueClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        accountSetupViewModel.updateYogaGoal(it)
                        navController.navigate(AccountSetupScreen.CurrentBodyShape.name)
                    }
                    )
            }

            composable(route = AccountSetupScreen.CurrentBodyShape.name) {
                SelectCurrentBodyShapeScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        navController.navigate(AccountSetupScreen.DesiredBodyShape.name)
                    },
                    onContinueClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        accountSetupViewModel.updateCurrentBodyShape(it)
                        navController.navigate(AccountSetupScreen.DesiredBodyShape.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.DesiredBodyShape.name) {
                SelectDesiredBodyShapeScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        navController.navigate(AccountSetupScreen.ExperienceLevel.name)
                    },
                    currentBodyShape = accountInfoUIState.currentBodyShape ?: 4,
                    onContinueClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        accountSetupViewModel.updateDesiredBodyShape(it)
                        navController.navigate(AccountSetupScreen.ExperienceLevel.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.ExperienceLevel.name) {
                ExperienceLevelScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        navController.navigate(AccountSetupScreen.Sedentary.name)
                    },
                    onContinueClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        accountSetupViewModel.updateExperienceLevel(it)
                        navController.navigate(AccountSetupScreen.Sedentary.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.Sedentary.name) {
                SedentaryLifestyleScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        navController.navigate(AccountSetupScreen.Plank.name)
                    },
                    onContinueClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        accountSetupViewModel.updateSedentaryLifestyle(it)
                        navController.navigate(AccountSetupScreen.Plank.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.Plank.name) {
                SelectPlankDurationScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        navController.navigate(AccountSetupScreen.LegRaises.name)
                    },
                    onContinueClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        accountSetupViewModel.updatePlank(it)
                        navController.navigate(AccountSetupScreen.LegRaises.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.LegRaises.name) {
                SelectLegRaiseDurationScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        navController.navigate(AccountSetupScreen.Age.name)
                    },
                    onContinueClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        accountSetupViewModel.updateLegRaise(it)
                        navController.navigate(AccountSetupScreen.Age.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.Age.name) {
                CollectAgeScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        navController.navigate(AccountSetupScreen.Height.name)
                    },
                    onContinueClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        navController.navigate(AccountSetupScreen.Height.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.Height.name) {
                CollectHeightScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        navController.navigate(AccountSetupScreen.CurrentWeight.name)
                    },
                    onContinueClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        accountSetupViewModel.updateHeight(it)
                        navController.navigate(AccountSetupScreen.CurrentWeight.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.CurrentWeight.name) {
                SelectCurrentBodyWeightScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        navController.navigate(AccountSetupScreen.TargetWeight.name)
                    },
                    onContinueClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        accountSetupViewModel.updateCurrentWeight(it)
                        navController.navigate(AccountSetupScreen.TargetWeight.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.TargetWeight.name) {
                SelectTargetBodyWeightScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        navController.navigate(AccountSetupScreen.YogaPlan.name)
                    },
                    onContinueClick = {
                        accountSetupViewModel.updateCurrentScreen()
                        accountSetupViewModel.updateTargetWeight(it)
                        navController.navigate(AccountSetupScreen.YogaPlan.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.YogaPlan.name) {
                SetYogaPlanScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {  },
                    onContinueClick = {
                        accountSetupViewModel.updateYogaWeekPlan(it)
                        navController.navigate(AccountSetupScreen.DisplayYogaPlan.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.DisplayYogaPlan.name) {
                DisplayYogaPlanScreen(
                    paddingValues = paddingValues,
                    onContinueClick = {
                        accountSetupViewModel.updateUserProfile()
                        (context as Activity).finish()
                        HomeActivity.startActivity(context as Activity)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountSetupTopAppBar(navController: NavHostController, navigateUp: () -> Unit = { }, uiState: AccountSetupUIState = AccountSetupUIState()) {

    val context = LocalContext.current

    when (navController.currentBackStackEntryAsState().value?.destination?.route) {
        AccountSetupScreen.SelectGender.name -> {
            CenterAlignedTopAppBar(
                title = {
                    LinearProgressIndicator(modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(10.dp)
                        .padding(horizontal = 30.dp), progress = uiState.currentScreen.div(14f), strokeCap = StrokeCap.Round)
                },
                navigationIcon = {   },
                actions = {
                    Text(
                        text = "${uiState.currentScreen}/$ACCOUNT_SETUP_MAX_SCREEN",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        }
        AccountSetupScreen.DisplayYogaPlan.name -> {
            CenterAlignedTopAppBar(
                title = {   },
                navigationIcon = {   },
                actions = { }
            )
        }
        else -> {
            CenterAlignedTopAppBar(
                title = {
                    LinearProgressIndicator(modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(10.dp)
                        .padding(horizontal = 30.dp), progress = uiState.currentScreen.div(14f), strokeCap = StrokeCap.Round)
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    Text(
                        text = "${uiState.currentScreen}/$ACCOUNT_SETUP_MAX_SCREEN",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            )
        }
    }
}

enum class AccountSetupScreen {
    SelectGender, SelectFocusArea,
    SelectYogaGoal, CurrentBodyShape,
    DesiredBodyShape, ExperienceLevel,
    Sedentary, Plank, LegRaises, Age,
    Height, CurrentWeight, TargetWeight,
    YogaPlan, DisplayYogaPlan

}