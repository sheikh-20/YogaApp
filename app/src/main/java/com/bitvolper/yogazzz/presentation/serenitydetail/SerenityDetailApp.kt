package com.bitvolper.yogazzz.presentation.serenitydetail

import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bitvolper.yogazzz.domain.model.SerenityData
import com.bitvolper.yogazzz.domain.model.YogaExercise
import com.bitvolper.yogazzz.presentation.viewmodel.AccountSetupViewModel
import com.bitvolper.yogazzz.presentation.viewmodel.AccountViewModel
import com.bitvolper.yogazzz.presentation.viewmodel.HomeViewModel
import com.bitvolper.yogazzz.utility.Resource

@Composable
fun SerenityDetailApp(modifier: Modifier = Modifier,
                      homeViewModel: HomeViewModel = hiltViewModel(),
                      accountViewModel: AccountViewModel = hiltViewModel()) {

    val yogaExerciseUIState by homeViewModel.serenityFlowUIState.collectAsState()

    Scaffold(
        topBar = { SerenityDetailTopAppBar(yogaExerciseUIState = yogaExerciseUIState, onBookmarkClick = { accountViewModel.updateBookmark(it) }) }
    ) { paddingValues ->
        SerenityDetailScreen(paddingValues = paddingValues, yogaExerciseUIState = yogaExerciseUIState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun SerenityDetailTopAppBar(yogaExerciseUIState: Resource<SerenityData> = Resource.Loading, onBookmarkClick: (String) -> Unit = { }) {
    val context = LocalContext.current

    CenterAlignedTopAppBar(
        title = {

        },
        navigationIcon = {
            IconButton(onClick = { (context as Activity).finish() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.Share, contentDescription = null)
            }

                when (yogaExerciseUIState) {
                    is Resource.Loading -> {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Rounded.BookmarkBorder, contentDescription = null)
                        }
                    }
                    is Resource.Failure -> {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Rounded.BookmarkBorder, contentDescription = null)
                        }
                    }
                    is Resource.Success -> {

                            IconButton(onClick = { onBookmarkClick(yogaExerciseUIState.data.data?.first()?.id ?: return@IconButton) }) {
                                Icon(imageVector = Icons.Rounded.Bookmark, contentDescription = null)
                            }
                    }
                }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
    )
}