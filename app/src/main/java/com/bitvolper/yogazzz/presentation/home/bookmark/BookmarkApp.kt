package com.bitvolper.yogazzz.presentation.home.bookmark

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.AppLanguagePreference
import com.bitvolper.yogazzz.presentation.viewmodel.AccountViewModel
import com.bitvolper.yogazzz.presentation.viewmodel.HomeViewModel
import com.bitvolper.yogazzz.utility.SetLanguage
import timber.log.Timber

@Composable
fun BookmarkApp(modifier: Modifier = Modifier,
                homeViewModel: HomeViewModel = hiltViewModel(),
                accountViewModel: AccountViewModel = hiltViewModel()) {

    val accountInfoUIState by accountViewModel.accountInfoUIState.collectAsState()
    val bookmarkUIState by homeViewModel.bookmarkUIState.collectAsState()

    Scaffold(
        topBar = { BookmarkTopAppBar() }
    ) { paddingValues ->
        BookmarkScreen(
            paddingValues = paddingValues,
            onBookmark = {
                accountViewModel.getUserProfile()

                if (accountInfoUIState.bookmark == null) {
                    homeViewModel.resetBookmark()
                } else {
                    homeViewModel.getBookmark(accountInfoUIState.bookmark ?: emptyList())
                }
            },
            bookmarkUIState = bookmarkUIState
            )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun BookmarkTopAppBar() {
    val context = LocalContext.current

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.bookmark),
                fontWeight = FontWeight.SemiBold)
        },
        navigationIcon = {
            IconButton(onClick = { (context as Activity).finish() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        },
        actions = {

        }
    )
}