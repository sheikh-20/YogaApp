package com.bitvolper.yogazzz.utility

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SignalCellularAlt
import androidx.compose.material.icons.rounded.SignalCellularAlt1Bar
import androidx.compose.material.icons.rounded.SignalCellularAlt2Bar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R

sealed class Resource<out T: Any> {
    object Loading: Resource<Nothing>()
    data class Success<out T: Any>(val data: T): Resource<T>()
    data class Failure(val throwable: Throwable): Resource<Nothing>()
}

@Composable
fun AccountSetupContinueComposable(modifier: Modifier = Modifier, onSkipClick: () -> Unit = { }, onContinueClick: () -> Unit = {  }) {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

        Divider(modifier = modifier.fillMaxWidth())

        Row(modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)) {

            OutlinedButton(onClick = onSkipClick, modifier = modifier
                .weight(1f)
                .requiredHeight(50.dp),
                border = BorderStroke(0.dp, Color.Transparent),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
                Text(text = "Skip", color = MaterialTheme.colorScheme.onPrimaryContainer)
            }

            Button(
                onClick = onContinueClick,
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

    data class BodyPartImage(
        val part: String,
        val image: Int,
    )
    
    val bodyPartsImage = listOf<BodyPartImage>(
        BodyPartImage(part = "Shoulders", image = R.drawable.ic_shoulders),
        BodyPartImage(part = "Chest", image = R.drawable.ic_chest),
        BodyPartImage(part = "Arms", image = R.drawable.ic_arms),
        BodyPartImage(part = "Back", image = R.drawable.ic_back),
        BodyPartImage(part = "Stomach", image = R.drawable.ic_stomach),
        BodyPartImage(part = "Legs", image = R.drawable.ic_legs),
    )
}

object Goal {
    data class YogaGoal(
        val name: String
    )

    val goals = listOf<YogaGoal>(
        YogaGoal(name = "\uD83C\uDFCB\uD83C\uDFFB \t\tWeight loss"),
        YogaGoal(name = "\uD83D\uDE34 \t\tBetter Sleep Quality"),
        YogaGoal(name = "\uD83E\uDDD8\uD83C\uDFFB \t\tBody Relaxation"),
        YogaGoal(name = "\uD83C\uDF4F \t\tImprove Health"),
        YogaGoal(name = "\uD83C\uDF2C\uFE0F \t\tRelieve Stress"),
        YogaGoal(name = "\uD83E\uDE70 \t\tPosture Correction")
    )
}

object Experience {
    data class  ExperienceLevel(
        val imageVector: ImageVector,
        val title: String,
        val description: String
    )

    val levels = listOf<ExperienceLevel>(
        ExperienceLevel(
            imageVector = Icons.Rounded.SignalCellularAlt1Bar, title = "Beginner", "I'm new to yoga",
        ),
        ExperienceLevel(
            imageVector = Icons.Rounded.SignalCellularAlt2Bar, title = "Intermediate", "I practice yoga regularly",
        ),
        ExperienceLevel(
            imageVector = Icons.Rounded.SignalCellularAlt, title = "Expert", "I'm experienced and living with yoga",
        )
    )
}

fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }


const val ACCOUNT_SETUP_MAX_SCREEN: Int = 14

data class HorizontalPagerContent(
    val duration: String, 
    val title: String,
    val price: String,
    val description: List<String>,
    val validity: String
)
