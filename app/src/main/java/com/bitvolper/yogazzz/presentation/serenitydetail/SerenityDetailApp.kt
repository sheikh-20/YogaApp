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
import com.bitvolper.yogazzz.domain.model.YogaExercise
import com.bitvolper.yogazzz.presentation.viewmodel.HomeViewModel
import com.bitvolper.yogazzz.utility.Resource

@Composable
fun SerenityDetailApp(modifier: Modifier = Modifier, homeViewModel: HomeViewModel = hiltViewModel()) {

    val yogaExerciseUIState by homeViewModel.yogaExerciseUIState.collectAsState()
    val bookmarkUIState by homeViewModel.bookmarkUIState.collectAsState()

    Scaffold(
        topBar = { SerenityDetailTopAppBar(bookmarkUIState = bookmarkUIState, onBookmarkClick = homeViewModel::updateBookmarkYogaExercise) }
    ) { paddingValues ->
        SerenityDetailScreen(paddingValues = paddingValues, yogaExerciseUIState = yogaExerciseUIState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun SerenityDetailTopAppBar(bookmarkUIState: Resource<YogaExercise> = Resource.Loading, onBookmarkClick: (Boolean) -> Unit = { }) {
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

                when (bookmarkUIState) {
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

                        if (bookmarkUIState.data.data?.size == 0) {
                            IconButton(onClick = { onBookmarkClick(true) }) {
                                Icon(imageVector = Icons.Rounded.BookmarkBorder, contentDescription = null)
                            }
                        } else {
                            IconButton(onClick = { onBookmarkClick(false) }) {
                                Icon(imageVector = Icons.Rounded.Bookmark, contentDescription = null)
                            }
                        }
                    }
                }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
    )
}