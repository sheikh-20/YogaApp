package com.bitvolper.yogazzz.presentation.serenitydetail

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Divider
import androidx.compose.material.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.VolumeOff
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.SerenityData
import com.bitvolper.yogazzz.presentation.serenitydetail.yogaexercise.NextYogaScreen
import com.bitvolper.yogazzz.presentation.serenitydetail.yogaexercise.PauseYogaScreen
import com.bitvolper.yogazzz.presentation.serenitydetail.yogaexercise.StartYogaScreen
import com.bitvolper.yogazzz.presentation.serenitydetail.yogaexercise.YogaCompletedScreen
import com.bitvolper.yogazzz.presentation.viewmodel.AccountViewModel
import com.bitvolper.yogazzz.presentation.viewmodel.ExerciseUIState
import com.bitvolper.yogazzz.presentation.viewmodel.HomeViewModel
import com.bitvolper.yogazzz.presentation.viewmodel.YogaExerciseViewModel
import com.bitvolper.yogazzz.utility.Resource
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "SerenityDetailApp"
@Composable
fun SerenityDetailApp(modifier: Modifier = Modifier,
                      navController: NavHostController = rememberNavController(),
                      homeViewModel: HomeViewModel = hiltViewModel(),
                      accountViewModel: AccountViewModel = hiltViewModel(),
                      yogaExerciseViewModel: YogaExerciseViewModel = hiltViewModel()) {

    val context = LocalContext.current

    val yogaExerciseUIState by homeViewModel.serenityFlowUIState.collectAsState()
    val currentExerciseUIState by yogaExerciseViewModel.currentExercise.collectAsState()
    val accountUIState by accountViewModel.accountInfoUIState.collectAsState()

    var showBottomSheet by remember { mutableStateOf(BottomSheet.Default) }

    when (showBottomSheet) {
        BottomSheet.Default -> {

        }
        BottomSheet.SoundEffect -> {
            BottomSheet(
                onDismiss = {
                    showBottomSheet = BottomSheet.Default
                },
                onNegativeClick = {
                    showBottomSheet = BottomSheet.Default
                },
                onPositiveClick = { showBottomSheet = BottomSheet.Default },
                contentSheet = { onPositiveClick, onNegativeClick ->
                    BottomSheetContent(
                        onPositiveClick = onPositiveClick,
                        onNegativeClick = onNegativeClick
                    )
                }
            )
        }
        BottomSheet.Subscription -> {
            BottomSheet(
                onDismiss = {
                    showBottomSheet = BottomSheet.Default
                },
                onNegativeClick = {
                    showBottomSheet = BottomSheet.Default
                },
                onPositiveClick = {
//                    (context as Activity).finish()
//                    SubscriptionActivity.startActivity(context as Activity)
                    showBottomSheet = BottomSheet.Default
                                  },
                contentSheet = { onPositiveClick, onNegativeClick ->
                    BottomSheetSubscriptionContent(
                        onPositiveClick = onPositiveClick,
                        onNegativeClick = onNegativeClick
                    )
                }
            )
        }
    }


    Timber.tag(TAG).d(yogaExerciseViewModel.currentExerciseIndex.inc().div(yogaExerciseViewModel.totalExerciseSize.toFloat()).toString())

    Scaffold(
        topBar = {
            SerenityDetailTopAppBar(
                navController = navController,
                yogaExerciseUIState = yogaExerciseUIState,
                onBookmarkAdd = { accountViewModel.updateBookmark(it) },
                onBookmarkRemove = { accountViewModel.removeBookmark(it) },
                showSoundEffect = {
                    yogaExerciseViewModel.changeSoundSettings {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                },
                currentExerciseIndex = yogaExerciseViewModel.currentExerciseIndex.inc().div(yogaExerciseViewModel.totalExerciseSize.toFloat()),
                currentExerciseUIState = currentExerciseUIState,
                isBookmark = accountUIState.bookmark,

                onPauseScreenResumeClick = {
                    yogaExerciseViewModel.resumeExerciseTimer()
                    navController.navigateUp()
                })
        }
    ) { paddingValues ->

        NavHost(
            navController = navController, startDestination = YogaExercise.Detail.name) {

            composable(route = YogaExercise.Detail.name) {
                SerenityDetailScreen(
                    paddingValues = paddingValues,
                    yogaExerciseUIState = yogaExerciseUIState,
                    onStartClick = {
                        yogaExerciseViewModel.startExercise(
                            serenityData = it,
                            nextScreen = { navController.navigate(YogaExercise.Next.name) },
                            completeScreen = { navController.navigate(YogaExercise.Complete.name) })
                        navController.navigate(YogaExercise.Start.name)
                    },
                    updateYogaId = accountViewModel::updateReports
                )
            }

            composable(route = YogaExercise.Start.name) {
                StartYogaScreen(
                    paddingValues = paddingValues,
                    currentYogaExercise = currentExerciseUIState,
                    onPauseClick = {
                        yogaExerciseViewModel.pauseExerciseTimer(
                            nextScreen = { navController.navigate(YogaExercise.Next.name) },
                            completeScreen = { navController.navigate(YogaExercise.Complete.name) }
                        )
                        navController.navigate(YogaExercise.Pause.name) },
                    onSkipClick = { yogaExerciseViewModel.skipExercise(
                        nextScreen = { navController.navigate(YogaExercise.Next.name) },
                        completeScreen = { navController.navigate(YogaExercise.Complete.name) }
                    ) },
                    onPreviousClick = { yogaExerciseViewModel.previousExercise(
                        nextScreen = { navController.navigate(YogaExercise.Next.name) },
                        completeScreen = { navController.navigate(YogaExercise.Complete.name) }
                    ) },
                    player = yogaExerciseViewModel.player,
                    onPlay = { yogaExerciseViewModel.playerStart(it) })
            }

            composable(route = YogaExercise.Pause.name) {
                PauseYogaScreen(
                    paddingValues = paddingValues,
                    currentYogaExercise = currentExerciseUIState,
                    onResumeClick = {
                        yogaExerciseViewModel.resumeExerciseTimer()
                        navController.navigateUp()
                    },
                    onRestartClick = {
                        yogaExerciseViewModel.restartExerciseTimer(
                        )
                        navController.popBackStack(YogaExercise.Detail.name, false)
                    })
            }

            composable(route = YogaExercise.Next.name) {
                NextYogaScreen(
                    paddingValues = paddingValues,
                    currentYogaExercise = currentExerciseUIState,
                    currentExerciseIndex = yogaExerciseViewModel.currentExerciseIndex,
                    totalExerciseSize = yogaExerciseViewModel.totalExerciseSize,
                    onStartRestTimer = { yogaExerciseViewModel.onRestTimerStart() },
                    onStopRestTimer = { yogaExerciseViewModel.onRestTimerStop() },
                    onSkipClick = {
                        navController.navigateUp()
                        yogaExerciseViewModel.startTimer()
                    }
                )
            }

            composable(route = YogaExercise.Complete.name) {
                YogaCompletedScreen(
                    paddingValues = paddingValues,
                    yogaExerciseUIState = yogaExerciseUIState,
                    showSubscriptionSheet = { showBottomSheet = BottomSheet.Subscription },
                    updateReports = accountViewModel::updateUserProfile,
                    updateHistory = accountViewModel::updateHistory)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheet(modifier: Modifier = Modifier,
                        onDismiss: () -> Unit = {},
                        onNegativeClick: () -> Unit = {},
                        onPositiveClick: () -> Unit = {},
                        contentSheet: @Composable (onNegativeClick: () -> Unit, onPositiveClick: () -> Unit) -> Unit = { _, _ -> }
) {

    val bottomSheet = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = {
            coroutineScope.launch {
                onDismiss()
                bottomSheet.hide()
            }
        },
        sheetState = bottomSheet,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp
    ) {

        contentSheet(onNegativeClick, onPositiveClick)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BottomSheetContent(modifier: Modifier = Modifier, onNegativeClick: () -> Unit = { }, onPositiveClick: () -> Unit = {}) {
    Column(modifier = modifier
        .padding(16.dp)
        .systemBarsPadding(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Sound Settings",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold)

        Divider()

        Row(modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.VolumeOff, contentDescription = null)
            }
            Text(text = "Mute Sound",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold)

            Spacer(modifier = modifier.weight(1f))

            Switch(checked = false, onCheckedChange = {  })
        }

        Row(modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.Mic, contentDescription = null)
            }
            Text(text = "Enable Voice Guide",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold)

            Spacer(modifier = modifier.weight(1f))

            Switch(checked = false, onCheckedChange = {  })
        }

        Row(modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.MusicNote, contentDescription = null)
            }
            Text(text = "Enable Sound Effect",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold)

            Spacer(modifier = modifier.weight(1f))

            Switch(checked = false, onCheckedChange = {  })
        }

        Divider()

        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            OutlinedButton(
                onClick = onNegativeClick,
                modifier = modifier
                    .weight(1f)
                    .requiredHeight(50.dp),
                border = BorderStroke(0.dp, Color.Transparent),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {

                Text(text = stringResource(id = R.string.cancel), color = MaterialTheme.colorScheme.onPrimaryContainer)
            }

            Button(onClick = onPositiveClick, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = "Okay")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BottomSheetSubscriptionContent(modifier: Modifier = Modifier, onNegativeClick: () -> Unit = { }, onPositiveClick: () -> Unit = {}) {
    Column(modifier = modifier
        .padding(16.dp)
        .systemBarsPadding(), verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Icon(
            imageVector = Icons.Rounded.Star,
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(text = stringResource(R.string.unlock_premium_benefits),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold)

        Text(
            text = stringResource(R.string.upgrade_to_yogazzz_premium_to_unlock_even_more_amazing_benefits_to_supercharge_your_yoga_journey),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
        )

        Divider()

        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            OutlinedButton(onClick = onNegativeClick, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp),
                border = BorderStroke(0.dp, Color.Transparent),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                Text(text = stringResource(R.string.not_now), color = MaterialTheme.colorScheme.onPrimaryContainer)
            }

            Button(onClick = onPositiveClick, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = stringResource(R.string.upgrade))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun SerenityDetailTopAppBar(navController: NavHostController? = null,
                                    yogaExerciseUIState: Resource<SerenityData> = Resource.Loading,
                                    currentExerciseUIState: ExerciseUIState = ExerciseUIState(),
                                    onBookmarkAdd: (String) -> Unit = { },
                                    onBookmarkRemove: (String) -> Unit = { },
                                    showSoundEffect: () -> Unit = {  },
                                    currentExerciseIndex: Float = 0f,
                                    isBookmark: List<String>? = listOf(),

                                    onPauseScreenResumeClick: () -> Unit = {  }) {

    val context = LocalContext.current

    when (navController?.currentBackStackEntryAsState()?.value?.destination?.route) {
        YogaExercise.Detail.name -> {
            CenterAlignedTopAppBar(
                title = {

                },
                navigationIcon = {
                    IconButton(onClick = { (context as Activity).finish() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null, tint = Color(0xFFFFFBFE))
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Rounded.Share, contentDescription = null, tint = Color(0xFFFFFBFE))
                    }

                    when (yogaExerciseUIState) {
                        is Resource.Loading -> {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Rounded.BookmarkBorder, contentDescription = null, tint = Color(0xFFFFFBFE))
                            }
                        }
                        is Resource.Failure -> {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Rounded.BookmarkBorder, contentDescription = null,  tint = Color(0xFFFFFBFE))
                            }
                        }
                        is Resource.Success -> {

                            if (isBookmark?.contains(yogaExerciseUIState.data.data?.first()?.id ?: return@CenterAlignedTopAppBar) == true) {

                                IconButton(onClick = {
                                    Toast.makeText(context,
                                        context.getString(R.string.bookmark_removed), Toast.LENGTH_SHORT).show()
                                    onBookmarkRemove(yogaExerciseUIState.data.data?.first()?.id ?: return@IconButton) }) {

                                    Icon(imageVector = Icons.Rounded.Bookmark, contentDescription = null, tint = Color(0xFFFFFBFE))
                                }
                            } else {
                                IconButton(onClick = {
                                    Toast.makeText(context,
                                        context.getString(R.string.bookmark_added), Toast.LENGTH_SHORT).show()
                                    onBookmarkAdd(yogaExerciseUIState.data.data?.first()?.id ?: return@IconButton) }) {

                                    Icon(imageVector = Icons.Rounded.BookmarkBorder, contentDescription = null, tint = Color(0xFFFFFBFE))
                                }
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        }
        YogaExercise.Pause.name -> {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.pause), fontWeight = FontWeight.SemiBold)
                },
                navigationIcon = {
                    IconButton(onClick = onPauseScreenResumeClick) {
                        Icon(imageVector = Icons.Rounded.Close, contentDescription = null)
                    }
                },
                actions = {

                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        }
        YogaExercise.Next.name -> {
            CenterAlignedTopAppBar(
                title = {

                },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigateUp() }) {
                        Icon(imageVector = Icons.Rounded.Close, contentDescription = null)
                    }
                },
                actions = {

                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        }
        YogaExercise.Complete.name -> {
            CenterAlignedTopAppBar(
                title = {

                },
                navigationIcon = {

                },
                actions = {

                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        }
        else -> {
            CenterAlignedTopAppBar(
                title = {
                    LinearProgressIndicator(modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(10.dp)
                        .padding(horizontal = 30.dp), progress = currentExerciseIndex, strokeCap = StrokeCap.Round)
                },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigateUp() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = showSoundEffect) {
                        if (currentExerciseUIState.soundEnabled) {
                            Icon(imageVector = Icons.Rounded.VolumeUp, contentDescription = null)
                        } else {
                            Icon(imageVector = Icons.Rounded.VolumeOff, contentDescription = null)
                        }
                    }
                }
            )
        }
    }
}

enum class BottomSheet {
    Default,
    SoundEffect,
    Subscription
}

enum class YogaExercise {
    Detail,
    Start, Pause,
    Next,
    Complete
}