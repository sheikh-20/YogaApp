package com.bitvolper.yogazzz.presentation.subscription

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bitvolper.yogazzz.presentation.viewmodel.AccountViewModel

@Composable
fun SubscriptionApp(modifier: Modifier = Modifier, accountViewModel: AccountViewModel = hiltViewModel()) {

    val subscriptionUIState by accountViewModel.subscriptionUIState.collectAsState()

    Scaffold(
        topBar = {
            SubscriptionTopAppBar()
        }
    ) { paddingValues ->
        SubscriptionScreen(paddingValues = paddingValues, subscriptionUIState = subscriptionUIState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubscriptionTopAppBar() {
    val context = LocalContext.current

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Upgrade Plan",
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