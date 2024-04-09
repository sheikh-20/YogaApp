package com.bitvolper.yogazzz.utility

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.concurrent.TimeUnit

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
                Text(text = stringResource(id = R.string.skip), color = MaterialTheme.colorScheme.onPrimaryContainer)
            }

            Button(
                onClick = onContinueClick,
                modifier = modifier
                    .weight(1f)
                    .requiredHeight(50.dp)) {

                Text(text = stringResource(id = R.string.continues))
            }
        }
    }
}

object Gender {
    data class GenderData(
        val image: Int, val title: Int
    )

    val man = GenderData(image = R.drawable.ic_male, title = R.string.man)
    val woman = GenderData(image = R.drawable.ic_female, title = R.string.woman)
}

object Body {
    data class BodyPart(
        @StringRes val part: Int
    )

    val bodyParts = listOf<BodyPart>(
        BodyPart(part = R.string.full_body),
        BodyPart(part = R.string.shoulders),
        BodyPart(part = R.string.chest),
        BodyPart(part = R.string.arms),
        BodyPart(part = R.string.back),
        BodyPart(part = R.string.stomach),
        BodyPart(part = R.string.legs)
    )

    data class BodyPartImage(
        @StringRes val part: Int,
        val image: Int,
        val color: Int
    )
    
    val bodyPartsImage = listOf<BodyPartImage>(
        BodyPartImage(part = R.string.shoulders, image = R.drawable.ic_shoulders, color = R.color.category1),
        BodyPartImage(part = R.string.chest, image = R.drawable.ic_chest, color = R.color.category2),
        BodyPartImage(part = R.string.arms, image = R.drawable.ic_arms, color = R.color.category3),
        BodyPartImage(part = R.string.back, image = R.drawable.ic_back, color = R.color.category4),
        BodyPartImage(part = R.string.stomach, image = R.drawable.ic_stomach, color = R.color.category5),
        BodyPartImage(part = R.string.legs, image = R.drawable.ic_legs, color = R.color.category6),
    )
}

object Goal {
    data class YogaGoal(
        @StringRes val name: Int
    )

    val goals = listOf<YogaGoal>(
        YogaGoal(name = R.string.weight_loss),
        YogaGoal(name = R.string.better_sleep_quality),
        YogaGoal(name = R.string.body_relaxation),
        YogaGoal(name = R.string.improve_health),
        YogaGoal(name = R.string.relieve_stress),
        YogaGoal(name = R.string.posture_correction)
    )
}

object Experience {
    data class  ExperienceLevel(
        val imageVector: ImageVector,
        @StringRes val title: Int,
        @StringRes val description: Int
    )

    val levels = listOf<ExperienceLevel>(
        ExperienceLevel(
            imageVector = Icons.Rounded.SignalCellularAlt1Bar, title = R.string.beginner, R.string.i_m_new_to_yoga,
        ),
        ExperienceLevel(
            imageVector = Icons.Rounded.SignalCellularAlt2Bar, title = R.string.intermediate, R.string.i_practice_yoga_regularly,
        ),
        ExperienceLevel(
            imageVector = Icons.Rounded.SignalCellularAlt, title = R.string.expert, R.string.i_m_experienced_and_living_with_yoga,
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

fun Long.formatMinSec(): String {
    return if (this == 0L) {
        "..."
    } else {
        String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(this),
            TimeUnit.MILLISECONDS.toSeconds(this) -
                    TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(this)
                    )
        )
    }
}


fun String.getDayOfWeek(): String {
    val format1 = SimpleDateFormat("d-M-yyyy")
    val dt1 = format1.parse(this)
    val format2: DateFormat = SimpleDateFormat("EEEE")
    return format2.format(dt1 ?: "")
}

//fun String.checkTodayDay(): String {
//    val format = SimpleDateFormat("EEE MMM yy")
//    val currentDate = format.parse(this)
//
//
//
////    val localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("EEE MMM yy"))
////
////    if (currentDate == localDate) {
////
////    }
//
//}
