package com.bitvolper.yogazzz.presentation.home

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Spa
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.bitvolper.yogazzz.presentation.home.account.AccountScreen
import com.bitvolper.yogazzz.presentation.home.bookmark.BookmarkActivity
import com.bitvolper.yogazzz.presentation.home.discover.DiscoverScreen
import com.bitvolper.yogazzz.presentation.home.history.HistoryScreen
import com.bitvolper.yogazzz.presentation.home.notification.NotificationSettingsActivity
import com.bitvolper.yogazzz.presentation.home.reports.ReportsScreen
import com.bitvolper.yogazzz.presentation.notifications.NotificationsActivity
import com.bitvolper.yogazzz.presentation.viewmodel.AccountViewModel
import com.bitvolper.yogazzz.presentation.viewmodel.DiscoverViewModel
import com.bitvolper.yogazzz.presentation.viewmodel.HomeViewModel
import com.bitvolper.yogazzz.presentation.viewmodel.OnboardingViewModel
import com.bitvolper.yogazzz.utility.Resource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeApp(modifier: Modifier = Modifier,
            navController: NavHostController = rememberNavController(),
            onboardingViewModel: OnboardingViewModel = hiltViewModel(),
            homeViewModel: HomeViewModel = hiltViewModel(),
            discoverViewModel: DiscoverViewModel = hiltViewModel(),
            accountViewModel: AccountViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var showBottomSheet by remember { mutableStateOf(BottomSheet.Default) }


    val profileUiState by homeViewModel.profileInfoUiState.collectAsState()
    val profilePhotoUIState by accountViewModel.profilePhotoUIState.collectAsState()

    val homeUIState by homeViewModel.homeUIState.collectAsState()
    val discoverUIState by discoverViewModel.discoverUIState.collectAsState()

    var notificationPermissionState = rememberMultiplePermissionsState(permissions = listOf())

    val historyUIState by homeViewModel.historyUIState.collectAsState()
    val reportsUIState by homeViewModel.reportsUIState.collectAsState()
    val accountInfoUIState by accountViewModel.accountInfoUIState.collectAsState()


    when (showBottomSheet) {
        BottomSheet.Default -> {}
        BottomSheet.Logout -> {
            BottomSheet(
                onDismiss = { showBottomSheet = BottomSheet.Default },
                onNegativeClick = { showBottomSheet = BottomSheet.Default },
                onPositiveClick = {
                    onboardingViewModel.signOut()
                    (context as Activity).finish()
                    HomeActivity.startActivity(context as Activity)
                },
                contentSheet = {
                        onNegativeClick, onPositiveClick ->

                    BottomSheetContent(
                        onNegativeClick = onNegativeClick,
                        onPositiveClick = onPositiveClick
                    )
                })
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

        notificationPermissionState = rememberMultiplePermissionsState(permissions = listOf(Manifest.permission.POST_NOTIFICATIONS))

        notificationPermissionState.permissions.forEach {
            when (it.permission) {
                Manifest.permission.POST_NOTIFICATIONS -> {
                    when {
                        it.hasPermission -> {
//                                        coroutineScope.launch {
//                                            snackbarHostState.showSnackbar(message = "Notification permission granted!", duration = SnackbarDuration.Short)
//                                        }
                        }
                        it.shouldShowRationale -> {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(message = "Notification permission is needed", duration = SnackbarDuration.Long)
                            }
                        }
                        it.hasPermission.not() && it.shouldShowRationale.not() -> {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(message = "Notification permission was permanently denied, You can enable it on app settings!", duration = SnackbarDuration.Long)
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionState.launchMultiplePermissionRequest()
        }
    }

    Scaffold(
        topBar = {
            HomeTopAppBar(navController = navController)
        },
        bottomBar = {
            HomeBottomBarNavigation(navController = navController)
        }
    ) { paddingValues ->

        NavHost(
            navController = navController, startDestination = BottomNavigationScreens.Home.route) {

            composable(route = BottomNavigationScreens.Home.route) {
                HomeScreen(paddingValues = paddingValues, homeUIState = homeUIState)
            }

            composable(route = BottomNavigationScreens.Discover.route) {
                DiscoverScreen(paddingValues = paddingValues, discoverUIState = discoverUIState)
            }

            composable(route = BottomNavigationScreens.Reports.route) {
                ReportsScreen(paddingValues = paddingValues,
                    onReports = {
                        accountViewModel.getUserProfile()

                        if (accountInfoUIState.reports == null) {
                            homeViewModel.resetReports()
                        } else {
                            homeViewModel.getReports(accountInfoUIState.reports ?: return@ReportsScreen)
                        }
                    },
                    reportsUIState = reportsUIState)
            }

            composable(route = BottomNavigationScreens.History.route) {
                HistoryScreen(paddingValues = paddingValues,
                    onHistory = {

                        accountViewModel.getUserProfile()

                        if (accountInfoUIState.history == null) {
                            homeViewModel.resetHistory()
                        } else {
                            homeViewModel.getHistory(accountInfoUIState.history ?: return@HistoryScreen)
                        } },
                    historyUIState = historyUIState)
            }

            composable(route = BottomNavigationScreens.Account.route) {
                AccountScreen(paddingValues = paddingValues,
                    uiState = profileUiState,
                    onSignOutClick = {
                        showBottomSheet = BottomSheet.Logout
                    },
                    profileUIState = profilePhotoUIState)
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
        Text(text = stringResource(R.string.logout),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold)

        Divider()

        Text(text = stringResource(R.string.are_you_sure_you_want_to_log_out),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth())

        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            OutlinedButton(onClick = onNegativeClick, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp),
                border = BorderStroke(0.dp, Color.Transparent),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                Text(text = stringResource(id = R.string.cancel), color = MaterialTheme.colorScheme.onPrimaryContainer)
            }

            Button(onClick = onPositiveClick, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = stringResource(R.string.yes_logout))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar(navController: NavHostController,) {

    val context = LocalContext.current

    when (navController.currentBackStackEntryAsState().value?.destination?.route) {
        BottomNavigationScreens.Home.route -> {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "YogazzZ",
                        fontWeight = FontWeight.SemiBold)
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Rounded.Spa, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { NotificationsActivity.startActivity(context as Activity) }) {
                        Icon(imageVector = Icons.Outlined.Notifications, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                    }

                    IconButton(onClick = { BookmarkActivity.startActivity(context as Activity) }) {
                        Icon(imageVector = Icons.Outlined.Bookmark, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                    }
                }
            )
        }
        BottomNavigationScreens.Discover.route -> {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Discover",
                        fontWeight = FontWeight.SemiBold)
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Rounded.Spa, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ },
                        modifier = Modifier.padding(horizontal = 4.dp)) {
                        Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                    }
                }
            )
        }
        BottomNavigationScreens.Reports.route -> {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Reports",
                        fontWeight = FontWeight.SemiBold)
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Rounded.Spa, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ },
                        modifier = Modifier.padding(horizontal = 4.dp)) {
                        Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                    }
                }
            )
        }

        BottomNavigationScreens.History.route -> {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "History",
                        fontWeight = FontWeight.SemiBold)
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Rounded.Spa, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ },
                        modifier = Modifier.padding(horizontal = 4.dp)) {
                        Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                    }
                }
            )
        }

        BottomNavigationScreens.Account.route -> {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Account",
                        fontWeight = FontWeight.SemiBold)
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Rounded.Spa, contentDescription = null)
                    }
                }
            )
        }
    }
}

enum class BottomSheet {
    Default, Logout
}

@Composable
private fun HomeBottomBarNavigation(navController: NavHostController) {

    val navigationBarItems = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Discover,
        BottomNavigationScreens.Reports,
        BottomNavigationScreens.History,
        BottomNavigationScreens.Account)

    NavigationBar(modifier = Modifier.clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        containerColor = Color.Transparent,
        tonalElevation = 3.dp
    ) {
        navigationBarItems.forEach {
            NavigationBarItem(
                selected = navController.currentBackStackEntryAsState().value?.destination?.route == it.route,
                onClick = {
                    navController.popBackStack()
                    navController.navigate(it.route)
                },
                icon = { Icon(painter = painterResource(id = it.drawableResource), contentDescription = null) },
                label = { Text(text = stringResource(id = it.stringResource)) },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = Color.Gray, selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = Color.Gray, selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                        LocalAbsoluteTonalElevation.current)
                ),
            )
        }
    }
}

sealed class BottomNavigationScreens(val route: String, @StringRes val stringResource: Int, @DrawableRes val drawableResource: Int) {
    object Home: BottomNavigationScreens(route = "Home", stringResource = R.string.home, drawableResource = R.drawable.ic_home)
    object Discover: BottomNavigationScreens(route = "Discover", stringResource = R.string.discover, drawableResource = R.drawable.ic_explore)
    object Reports: BottomNavigationScreens(route = "Reports", stringResource = R.string.reports, drawableResource = R.drawable.ic_reports)
    object History: BottomNavigationScreens(route = "History", stringResource = R.string.history, drawableResource = R.drawable.ic_history)
    object Account: BottomNavigationScreens(route = "Account", stringResource = R.string.account, drawableResource = R.drawable.ic_account)
}