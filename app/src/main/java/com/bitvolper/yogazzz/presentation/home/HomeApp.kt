package com.bitvolper.yogazzz.presentation.home

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Spa
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
import com.bitvolper.yogazzz.presentation.home.notification.NotificationActivity
import com.bitvolper.yogazzz.presentation.home.reports.ReportsScreen

@Composable
fun HomeApp(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
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
                HomeScreen(paddingValues = paddingValues)
            }

            composable(route = BottomNavigationScreens.Discover.route) {
                DiscoverScreen(paddingValues = paddingValues)
            }

            composable(route = BottomNavigationScreens.Reports.route) {
                ReportsScreen(paddingValues = paddingValues)
            }

            composable(route = BottomNavigationScreens.History.route) {
                HistoryScreen(paddingValues = paddingValues)
            }

            composable(route = BottomNavigationScreens.Account.route) {
                AccountScreen(paddingValues = paddingValues)
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
                        text = stringResource(id = R.string.app_name),
                        fontWeight = FontWeight.SemiBold)
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Rounded.Spa, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { NotificationActivity.startActivity(context as Activity) },
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .shadow(
                                elevation = 8.dp,
                                ambientColor = Color.Transparent,
                                spotColor = Color.Transparent
                            )
                            .background(
                                shape = RoundedCornerShape(50),
                                color = MaterialTheme.colorScheme.surface
                            )
                            .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(50))) {
                        Icon(imageVector = Icons.Outlined.Notifications, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                    }

                    IconButton(onClick = { BookmarkActivity.startActivity(context as Activity) },
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .shadow(
                                elevation = 8.dp,
                                ambientColor = Color.Transparent,
                                spotColor = Color.Transparent
                            )
                            .background(
                                shape = RoundedCornerShape(50),
                                color = MaterialTheme.colorScheme.surface
                            )
                            .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(50))
                    ) {
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