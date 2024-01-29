package com.bitvolper.yogazzz.utility

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

sealed class Resource<out T: Any> {
    object Loading: Resource<Nothing>()
    data class Success<out T: Any>(val data: T): Resource<T>()
    data class Failure(val throwable: Throwable): Resource<Nothing>()
}

@Composable
fun AccountSetupContinueComposable(modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

        Divider(modifier = modifier.fillMaxWidth())

        Row(modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {

            OutlinedButton(onClick = {  }, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp)) {
                Text(text = "Skip")
            }

            Button(
                onClick = {   },
                modifier = modifier
                    .weight(1f)
                    .requiredHeight(50.dp)) {

                Text(text = "Continue")
            }
        }
    }
}