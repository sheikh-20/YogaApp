package com.bitvolper.yogazzz.presentation.home.discover.meditation

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.home.BottomNavigationScreens
import com.bitvolper.yogazzz.presentation.home.HomeScreen
import com.bitvolper.yogazzz.presentation.viewmodel.AccountViewModel
import com.bitvolper.yogazzz.presentation.viewmodel.DiscoverViewModel
import com.bitvolper.yogazzz.presentation.viewmodel.MeditationViewModel

@Composable
fun MeditationApp(modifier: Modifier = Modifier,
                  navController: NavHostController = rememberNavController(),
                  discoverViewModel: DiscoverViewModel = hiltViewModel(),
                  meditationViewModel: MeditationViewModel = hiltViewModel(),
                  accountViewModel: AccountViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val meditationUIState by discoverViewModel.meditationUIState.collectAsState()
    val playerUIState by meditationViewModel.playerStreamUIState.collectAsState()

    Scaffold(
        topBar = { MeditationTopAppBar(navController = navController, onMeditationStop = meditationViewModel::onMeditationStop) }
    ) { paddingValues ->

        NavHost(
            navController = navController, startDestination = Meditation.Start.name) {

            composable(route = Meditation.Start.name) {
                MeditationStartScreen(
                    meditationUIState = meditationUIState,
                    onCardClick = {
                        discoverViewModel.updateMeditation(it)
                        navController.navigate(Meditation.Detail.name)
                    })
            }

            composable(route = Meditation.Detail.name) {
                MeditationDetailScreen(paddingValues = paddingValues,
                    onContinueScreen = { navController.navigate(Meditation.Play.name) },
                    meditation = discoverViewModel.meditation)
            }

            composable(route = Meditation.Play.name) {
                MeditationPlayScreen(
                    paddingValues = paddingValues,
                    meditation = discoverViewModel.meditation,
                    onPlayMeditation = meditationViewModel::playVideoStream,
                    onMeditationStop = {
                        meditationViewModel.onMeditationStop()
                        navController.navigateUp()
                    },
                    onPlayPauseClick = meditationViewModel::playOrPauseVideo,
                    playerUIState = playerUIState,
                    onSeekTo = meditationViewModel::onSeekTo,
                    showCompleteScreen = {
                        meditationViewModel.onMeditationStop()
                        navController.navigate(Meditation.Complete.name)
                    })
            }

            composable(route = Meditation.Complete.name) {
                MeditationCompleteScreen(
                    paddingValues = paddingValues,
                    meditation = discoverViewModel.meditation,
                    onCloseButtonClick = { (context as Activity).finish() },
                    onCompleteClick = {
                        accountViewModel.updateHistory(it)
                        (context as Activity).finish()
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MeditationTopAppBar(navController: NavHostController, onMeditationStop: () -> Unit = { }) {
    val context = LocalContext.current

    when (navController.currentBackStackEntryAsState().value?.destination?.route) {
        Meditation.Start.name -> {
            CenterAlignedTopAppBar(
                title = {   },
                navigationIcon = {
                    IconButton(onClick = { (context as Activity).finish() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        }
        Meditation.Detail.name -> {

            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.meditation),
                        fontWeight = FontWeight.SemiBold)
                },
                navigationIcon = {
                    IconButton(onClick = {  navController.navigateUp() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ },
                        modifier = Modifier.padding(horizontal = 4.dp)) {
                        Icon(imageVector = Icons.Rounded.Share, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                    }
                }
            )
        }
        Meditation.Play.name -> {
            CenterAlignedTopAppBar(
                title = {   },
                navigationIcon = {
                    IconButton(onClick = {
                        onMeditationStop()
                        navController.navigateUp()
                    }) {
                        Icon(imageVector = Icons.Rounded.Close, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        }
        Meditation.Complete.name -> {
            CenterAlignedTopAppBar(
                title = {   },
                navigationIcon = {
                    IconButton(onClick = { (context as Activity).finish() }) {
                        Icon(imageVector = Icons.Rounded.Close, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        }
    }
}

enum class Meditation {
    Start, Detail, Play, Complete
}