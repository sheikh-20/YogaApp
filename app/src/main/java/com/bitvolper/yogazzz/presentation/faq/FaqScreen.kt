package com.bitvolper.yogazzz.presentation.faq

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.domain.model.FaqQuestion
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.Resource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun FaqScreen(modifier: Modifier = Modifier,
              paddingValues: PaddingValues = PaddingValues(),
              faqUIState: Resource<FaqQuestion> = Resource.Loading) {

    val category = listOf(Pair(0, "General"), Pair(1, "Account"), Pair(2, "Services"), Pair(3, "Subscription"))

    var selectedIndex by remember { mutableIntStateOf(0) }

    val color = MaterialTheme.colorScheme.primary

    Column(modifier = modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)) {

        when (faqUIState) {
            is Resource.Loading -> {
                CircularProgressIndicator()
            }
            is Resource.Failure -> {
                Text(text = faqUIState.throwable.toString())
            }
            is Resource.Success -> {

                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        label = { Text(text = "Search") },
                        shape = RoundedCornerShape(20),
                        modifier = modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = null
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                    )

                    Row(modifier = modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                        category.forEach {
                            Chip(onClick = { selectedIndex = it.first }, colors = ChipDefaults.chipColors(backgroundColor = if (selectedIndex == it.first) color else Color.Transparent), border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)) {
                                Text(text = it.second)
                            }
                        }
                    }

                    val question = faqUIState.data.data?.filter {
                        it?.category == selectedIndex
                    }

                    question?.forEach {
                        FaqCard(faq = it ?: return@forEach)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun FaqCard(modifier: Modifier = Modifier, faq: FaqQuestion.Data = FaqQuestion.Data()) {

    var expanded by remember { mutableStateOf("") }

    Card() {
        Column(modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Row(modifier = modifier.fillMaxWidth()) {
                Text(
                    text = faq.question ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier.weight(1f)
                )

                IconButton(onClick = { expanded = faq.question ?: "" }) {
                    Icon(imageVector = if (expanded == faq.question) Icons.Rounded.ExpandMore else Icons.Rounded.ExpandLess, contentDescription = null)
                }
            }

            if (expanded == faq.question) {
                Divider(color = MaterialTheme.colorScheme.outline)

                Text(text = faq.ans ?: "",
                    style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FaqLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        FaqScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FaqDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        FaqScreen()
    }
}