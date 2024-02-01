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
import com.bitvolper.yogazzz.R

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

object Gender {
    data class GenderData(
        val image: Int, val title: String
    )

    val man = GenderData(image = R.drawable.ic_male, title = "Man")
    val woman = GenderData(image = R.drawable.ic_female, title = "Woman")
}

object Body {
    data class BodyPart(
        val part: String
    )

    val bodyParts = listOf<BodyPart>(
        BodyPart(part = "Full Body"),
        BodyPart(part = "Shoulders"),
        BodyPart(part = "Chest"),
        BodyPart(part = "Arms"),
        BodyPart(part = "Back"),
        BodyPart(part = "Stomach"),
        BodyPart(part = "Legs")
    )
}

object Goal {
    data class YogaGoal(
        val name: String
    )

    val goals = listOf<YogaGoal>(
        YogaGoal(name = "Weight loss"),
        YogaGoal(name = "Better Sleep Quality"),
        YogaGoal(name = "Body Relaxation"),
        YogaGoal(name = "Improve Health"),
        YogaGoal(name = "Relieve Stress"),
        YogaGoal(name = "Posture Correction")
    )
}