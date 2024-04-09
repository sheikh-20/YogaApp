package com.bitvolper.yogazzz.presentation.home.discover.flexiblity_strength

import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.FlexibilityStrength
import com.bitvolper.yogazzz.presentation.viewmodel.DiscoverViewModel

@Composable
fun FlexibilityStrengthApp(modifier: Modifier = Modifier, discoverViewModel: DiscoverViewModel = hiltViewModel()) {

    val flexibilityStrengthUIState by discoverViewModel.flexibilityStrength.collectAsState()

    Scaffold(
        topBar = { FlexibilityStrengthTopAppBar() }
    ) { paddingValues ->
        FlexibilityStrengthScreen(paddingValues = paddingValues, flexibilityStrengthUIState = flexibilityStrengthUIState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FlexibilityStrengthTopAppBar() {
    val context = LocalContext.current

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.flexibility_strength),
                fontWeight = FontWeight.SemiBold)
        },
        navigationIcon = {
            IconButton(onClick = { (context as Activity).finish() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        }
    )
}