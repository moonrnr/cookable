package com.example.cookable.ui.components.filterbottomsheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookable.core.util.isTimeFormatValid
import com.example.cookable.core.util.isTimeInRange
import com.example.cookable.ui.theme.Line
import com.example.cookable.ui.theme.PrimaryGreen
import com.example.cookable.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    allTags: List<String>,
    state: FilterBottomSheetState,
    onStateChange: (FilterBottomSheetState) -> Unit,
    onApply: () -> Unit,
    onDismiss: () -> Unit,
    onReset: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        tonalElevation = 0.dp,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = {
            Box(
                modifier =
                    Modifier
                        .padding(top = 8.dp)
                        .size(width = 40.dp, height = 4.dp)
                        .background(Color.LightGray, RoundedCornerShape(2.dp)),
            )
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .navigationBarsPadding()
                        .padding(
                            start = 0.dp,
                            end = 0.dp,
                            top = 0.dp,
                            bottom = 120.dp,
                        ),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 18.dp, top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Filters",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    if (!state.isEmpty) {
                        TextButton(
                            onClick = onReset,
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.height(24.dp).width(120.dp).padding(bottom = 2.dp),
                        ) {
                            Text(
                                text = "Reset filters",
                                fontSize = 18.sp,
                                color = PrimaryGreen,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.padding(end = 0.dp).height(40.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))
                HorizontalDivider(color = Line)
                Spacer(Modifier.height(12.dp))

                Column(
                    modifier = Modifier.padding(start = 18.dp, end = 18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    FilterSection("Tags") {
                        TagFilterInput(
                            allTags = allTags,
                            query = state.tagQuery,
                            selectedTags = state.selectedTags,
                            onQueryChange = {
                                onStateChange(state.copy(tagQuery = it))
                            },
                            onTagSelected = { tag ->
                                onStateChange(
                                    state.copy(
                                        selectedTags = state.selectedTags + tag,
                                        tagQuery = "",
                                    ),
                                )
                            },
                            onTagRemoved = { tag ->
                                onStateChange(
                                    state.copy(
                                        selectedTags = state.selectedTags - tag,
                                    ),
                                )
                            },
                        )
                    }

                    FilterSection("Match level") {
                        CheckboxItem("Perfect", state.matchPerfect) {
                            onStateChange(state.copy(matchPerfect = it))
                        }
                        CheckboxItem("High", state.matchHigh) {
                            onStateChange(state.copy(matchHigh = it))
                        }
                        CheckboxItem("Medium", state.matchMedium) {
                            onStateChange(state.copy(matchMedium = it))
                        }
                        CheckboxItem("Low", state.matchLow) {
                            onStateChange(state.copy(matchLow = it))
                        }
                    }

                    HorizontalDivider(color = Line)

                    FilterSection(title = "Diet") {
                        CheckboxItem("Vegetarian", state.vegetarian) {
                            onStateChange(state.copy(vegetarian = it))
                        }
                        CheckboxItem("Vegan", state.vegan) {
                            onStateChange(state.copy(vegan = it))
                        }
                        CheckboxItem("Gluten free", state.glutenFree) {
                            onStateChange(state.copy(glutenFree = it))
                        }
                        CheckboxItem("Keto", state.keto) {
                            onStateChange(state.copy(keto = it))
                        }
                    }
                    HorizontalDivider(color = Line)

                    FilterSection("Meal type") {
                        CheckboxItem("Breakfast", state.breakfast) {
                            onStateChange(state.copy(breakfast = it))
                        }
                        CheckboxItem("Lunch", state.lunch) {
                            onStateChange(state.copy(lunch = it))
                        }
                        CheckboxItem("Dinner", state.dinner) {
                            onStateChange(state.copy(dinner = it))
                        }
                        CheckboxItem("Supper", state.supper) {
                            onStateChange(state.copy(supper = it))
                        }
                    }

                    HorizontalDivider(color = Line)

                    FilterSection("Difficulty") {
                        CheckboxItem("Easy", state.difficultyEasy) {
                            onStateChange(state.copy(difficultyEasy = it))
                        }
                        CheckboxItem("Medium", state.difficultyMedium) {
                            onStateChange(state.copy(difficultyMedium = it))
                        }
                        CheckboxItem("Hard", state.difficultyHard) {
                            onStateChange(state.copy(difficultyHard = it))
                        }
                    }

                    HorizontalDivider(color = Line)

                    FilterSection("Time (minutes)") {
                        val minFormatValid = isTimeFormatValid(state.minTime)
                        val maxFormatValid = isTimeFormatValid(state.maxTime)

                        val minRangeValid = isTimeInRange(state.minTime)
                        val maxRangeValid = isTimeInRange(state.maxTime)

                        val hasFormatError =
                            (state.minTime.isNotBlank() && !minFormatValid) ||
                                (state.maxTime.isNotBlank() && !maxFormatValid)

                        val hasRangeError =
                            !hasFormatError &&
                                (
                                    (state.minTime.isNotBlank() && !minRangeValid) ||
                                        (state.maxTime.isNotBlank() && !maxRangeValid)
                                )

                        Column {
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                OutlinedTextField(
                                    value = state.minTime,
                                    onValueChange = {
                                        onStateChange(state.copy(minTime = it))
                                    },
                                    keyboardOptions =
                                        KeyboardOptions(
                                            keyboardType = KeyboardType.Number,
                                        ),
                                    isError = state.minTime.isNotBlank() && (!minFormatValid || !minRangeValid),
                                    label = { Text("Min") },
                                    modifier = Modifier.weight(1f),
                                )
                                OutlinedTextField(
                                    value = state.maxTime,
                                    onValueChange = {
                                        onStateChange(state.copy(maxTime = it))
                                    },
                                    keyboardOptions =
                                        KeyboardOptions(
                                            keyboardType = KeyboardType.Number,
                                        ),
                                    isError = state.maxTime.isNotBlank() && (!maxFormatValid || !maxRangeValid),
                                    label = { Text("Max") },
                                    modifier = Modifier.weight(1f),
                                )
                            }

                            if (hasFormatError || hasRangeError) {
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text =
                                        when {
                                            hasFormatError ->
                                                "Enter a valid number (digits with optional . or ,)"
                                            else ->
                                                "Time must be between 1 and 1440 minutes"
                                        },
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 12.sp,
                                )
                            }
                        }
                    }
                }
            }

            Row(
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(White)
                        .navigationBarsPadding()
                        .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier =
                        Modifier
                            .weight(1f)
                            .height(52.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors =
                        ButtonDefaults.outlinedButtonColors(
                            contentColor = PrimaryGreen,
                        ),
                    border = BorderStroke(1.dp, Line),
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Button(
                    onClick = onApply,
                    modifier =
                        Modifier
                            .weight(1f)
                            .height(52.dp),
                    enabled = !state.isEmpty,
                    shape = RoundedCornerShape(18.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = PrimaryGreen,
                            disabledContainerColor = Color(0xFFDADCE0),
                            contentColor = Color.White,
                            disabledContentColor = Color(0xFF9AA0A6),
                        ),
                    elevation =
                        ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp,
                        ),
                ) {
                    Text(
                        text = "Apply",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
            }
        }
    }
}
