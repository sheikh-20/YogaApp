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
                SelectGenderScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        navController.navigate(AccountSetupScreen.SelectFocusArea.name)
                    },
                    onContinueClick = {
                        navController.navigate(AccountSetupScreen.SelectFocusArea.name)
                    })
            }

            composable(route = AccountSetupScreen.SelectFocusArea.name) {
                SelectFocusAreaScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        navController.navigate(AccountSetupScreen.SelectYogaGoal.name)
                    },
                    onContinueClick = {
                        navController.navigate(AccountSetupScreen.SelectYogaGoal.name)
                    }
                    )
            }

            composable(route = AccountSetupScreen.SelectYogaGoal.name) {
                YogaGoalScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        navController.navigate(AccountSetupScreen.CurrentBodyShape.name)
                    },
                    onContinueClick = {
                        navController.navigate(AccountSetupScreen.CurrentBodyShape.name)
                    }
                    )
            }

            composable(route = AccountSetupScreen.CurrentBodyShape.name) {
                SelectCurrentBodyShapeScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        navController.navigate(AccountSetupScreen.DesiredBodyShape.name)
                    },
                    onContinueClick = {
                        navController.navigate(AccountSetupScreen.DesiredBodyShape.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.DesiredBodyShape.name) {
                SelectDesiredBodyShapeScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        navController.navigate(AccountSetupScreen.ExperienceLevel.name)
                    },
                    onContinueClick = {
                        navController.navigate(AccountSetupScreen.ExperienceLevel.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.ExperienceLevel.name) {
                ExperienceLevelScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        navController.navigate(AccountSetupScreen.Sedentary.name)
                    },
                    onContinueClick = {
                        navController.navigate(AccountSetupScreen.Sedentary.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.Sedentary.name) {
                SedentaryLifestyleScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        navController.navigate(AccountSetupScreen.Plank.name)
                    },
                    onContinueClick = {
                        navController.navigate(AccountSetupScreen.Plank.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.Plank.name) {
                SelectPlankDurationScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        navController.navigate(AccountSetupScreen.LegRaises.name)
                    },
                    onContinueClick = {
                        navController.navigate(AccountSetupScreen.LegRaises.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.LegRaises.name) {
                SelectLegRaiseDurationScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        navController.navigate(AccountSetupScreen.Age.name)
                    },
                    onContinueClick = {
                        navController.navigate(AccountSetupScreen.Age.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.Age.name) {
                CollectAgeScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        navController.navigate(AccountSetupScreen.Height.name)
                    },
                    onContinueClick = {
                        navController.navigate(AccountSetupScreen.Height.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.Height.name) {
                CollectHeightScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        navController.navigate(AccountSetupScreen.CurrentWeight.name)
                    },
                    onContinueClick = {
                        navController.navigate(AccountSetupScreen.CurrentWeight.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.CurrentWeight.name) {
                SelectCurrentBodyWeightScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        navController.navigate(AccountSetupScreen.TargetWeight.name)
                    },
                    onContinueClick = {
                        navController.navigate(AccountSetupScreen.TargetWeight.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.TargetWeight.name) {
                SelectTargetBodyWeightScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {
                        navController.navigate(AccountSetupScreen.YogaPlan.name)
                    },
                    onContinueClick = {
                        navController.navigate(AccountSetupScreen.YogaPlan.name)
                    }
                )
            }

            composable(route = AccountSetupScreen.YogaPlan.name) {
                SetYogaPlanScreen(
                    paddingValues = paddingValues,
                    onSkipClick = {  },
                    onContinueClick = {  }
                )
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
            LinearProgressIndicator(modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(10.dp)
                .padding(horizontal = 30.dp), progress = .1f, strokeCap = StrokeCap.Round)
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
    SelectGender, SelectFocusArea,
    SelectYogaGoal, CurrentBodyShape,
    DesiredBodyShape, ExperienceLevel,
    Sedentary, Plank, LegRaises, Age,
    Height, CurrentWeight, TargetWeight,
    YogaPlan

}