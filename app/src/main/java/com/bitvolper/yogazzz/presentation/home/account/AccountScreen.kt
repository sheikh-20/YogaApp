package com.bitvolper.yogazzz.presentation.home.account

import android.app.Activity
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.RemoveRedEye
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Spa
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material.icons.rounded.SupportAgent
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.AccountInfo
import com.bitvolper.yogazzz.domain.model.UserData
import com.bitvolper.yogazzz.presentation.accountsecurity.AccountSecurityActivity
import com.bitvolper.yogazzz.presentation.analytics.AnalyticsActivity
import com.bitvolper.yogazzz.presentation.appearance.AppearanceActivity
import com.bitvolper.yogazzz.presentation.billing.BillingSubscriptionActivity
import com.bitvolper.yogazzz.presentation.home.notification.NotificationSettingsActivity
import com.bitvolper.yogazzz.presentation.mybody.MyBodyActivity
import com.bitvolper.yogazzz.presentation.notifications.NotificationsActivity
import com.bitvolper.yogazzz.presentation.subscription.SubscriptionActivity
import com.bitvolper.yogazzz.presentation.support.SupportActivity
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.presentation.userprofile.UserProfileActivity
import com.bitvolper.yogazzz.utility.Resource

@Composable
fun AccountScreen(modifier: Modifier = Modifier,
                  paddingValues: PaddingValues = PaddingValues(),
                  uiState: UserData? = null,
                  accountInfoUIState: AccountInfo = AccountInfo(),
                  profileUIState: Resource<Uri> = Resource.Loading,
                  onSignOutClick: () -> Unit = {}) {

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val context = LocalContext.current

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            bottom = paddingValues.calculateBottomPadding(),
            start = 16.dp, end = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {

        ProfileViewCompose(
            userName = accountInfoUIState.fullName ?: "",
            gmail = uiState?.email ?: "",
            onClick = {
                UserProfileActivity.startActivity(context as Activity)
            },
            profileUIState = profileUIState
        )

        SubscriptionCompose {
            SubscriptionActivity.startActivity(context as Activity)
        }

        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            AccountContentCompose(title = stringResource(R.string.my_body), icon = Icons.Rounded.Person) {
                MyBodyActivity.startActivity(context as Activity)
            }

            AccountContentCompose(title = stringResource(R.string.notifications), icon = Icons.Rounded.Notifications) {
                NotificationSettingsActivity.startActivity(context as Activity)
            }

            AccountContentCompose(title = stringResource(R.string.account_security), icon = Icons.Rounded.Security) {
                AccountSecurityActivity.startActivity(context as Activity)
            }

            AccountContentCompose(title = stringResource(R.string.billing_subscription), icon = Icons.Rounded.StarBorder) {
                BillingSubscriptionActivity.startActivity(context as Activity)
            }

            AccountContentCompose(title = stringResource(R.string.app_appearance), icon = Icons.Rounded.RemoveRedEye) {
                AppearanceActivity.startActivity(context as Activity)
            }

//            AccountContentCompose(title = "Data & Analytics", icon = Icons.Rounded.Analytics) {
//                AnalyticsActivity.startActivity(context as Activity)
//            }

            AccountContentCompose(title = stringResource(R.string.help_support), icon = Icons.Rounded.SupportAgent) {
                SupportActivity.startActivity(context as Activity)
            }

            Row(modifier = modifier
                .fillMaxWidth()
                .clickable(
                    onClick = onSignOutClick,
                    interactionSource = interactionSource,
                    indication = null
                )
                .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Rounded.Logout, contentDescription = null, tint = Color.Red)

                Spacer(modifier = modifier.width(8.dp))

                Text(
                    text = stringResource(id = R.string.logout),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier
                        .weight(1f)
                        .clickable(
                            onClick = onSignOutClick,
                            interactionSource = interactionSource,
                            indication = null
                        ),
                    color = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileViewCompose(modifier: Modifier = Modifier, userName: String = "Sheikh", gmail: String = "sheik@gmail.com", onClick: () -> Unit = {}, profileUIState: Resource<Uri> = Resource.Loading) {

        Row(modifier = modifier
            .clickable(onClick = onClick, interactionSource = remember {
                MutableInteractionSource()
            }, indication = null)
            .fillMaxWidth()
            .padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {

            IconButton(onClick = {  }, modifier = modifier.size(60.dp)) {

                when (profileUIState) {
                    is Resource.Loading -> {
                        Icon(painterResource(id = R.drawable.ic_image_placeholder),
                            contentDescription = null,
                            modifier = modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(50)))
                    }

                    is Resource.Failure -> {
                        Icon(imageVector = Icons.Rounded.Spa,
                            contentDescription = null,
                            modifier = modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(50))
                                .padding(16.dp))
                    }

                    is Resource.Success -> {
                        AsyncImage(
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(profileUIState.data)
                                .crossfade(true)
                                .build(),
                            error = painterResource(id = R.drawable.ic_broken_image),
                            placeholder = painterResource(id = R.drawable.ic_image_placeholder),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(50)),
                        )
                    }
                }
            }

            Column(modifier = modifier.weight(1f)) {

                Text(text = userName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold)

                Text(text = gmail, style = MaterialTheme.typography.bodyMedium)
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector =  Icons.Rounded.ArrowForwardIos, contentDescription = null)
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun SubscriptionCompose(modifier: Modifier = Modifier, onClick: () -> Unit = { }) {

    Card(onClick = onClick) {
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(8.dp), verticalAlignment = Alignment.CenterVertically) {

            Icon(imageVector = Icons.Outlined.Star, contentDescription = null, modifier = modifier
                .size(70.dp)
                .padding(4.dp))

            Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = stringResource(R.string.upgrade_plan_to_unlock_more),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold)

                Text(text = stringResource(R.string.enjoy_all_the_benefits_and_explore_more_possibilities), style = MaterialTheme.typography.bodySmall)
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountContentCompose(modifier: Modifier = Modifier, icon: ImageVector = Icons.Outlined.Edit, title: String = "Edit Store Details", onClick: () -> Unit = { }) {
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable(onClick = onClick, interactionSource = remember {
            MutableInteractionSource()
        }, indication = null)
        .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null)

        Spacer(modifier = modifier.width(8.dp))

        Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = modifier.weight(1f))

        Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AccountLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        AccountScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AccountDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        AccountScreen()
    }
}

