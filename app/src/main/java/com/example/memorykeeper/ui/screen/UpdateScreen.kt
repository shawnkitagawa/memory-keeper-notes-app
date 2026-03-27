package com.example.memorykeeper.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.memorykeeper.ui.uistate.FilterStatus
import com.example.memorykeeper.ui.uistate.FormState
import com.example.memorykeeper.ui.uistate.Todo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTodoField(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    isRoutine: Boolean,
    selectedCategory: String?,
    updateTitle: (String) -> Unit,
    updateDescription: (String) -> Unit,
    updateIsRoutine: (Boolean) -> Unit,
    updateCategory: (FilterStatus) -> Unit,
    updateTodo: Todo,
    setUpdateToDo: (Todo) -> Unit,
    updateTodoData: (String) -> Unit,
    navBack: () -> Unit,
    formcheck: FormState,
    resetFormState: () -> Unit
) {
    LaunchedEffect(Unit) {
        setUpdateToDo(updateTodo)
    }
    LaunchedEffect(formcheck) {
        if (formcheck is FormState.Success)
        {
            navBack()
            resetFormState()

        }
    }
    val categories = listOf(
        FilterStatus.WORK to "Work",
        FilterStatus.PERSONAL to "Personal",
        FilterStatus.HEALTH to "Health"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Update",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Refine the mission.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Title",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    OutlinedTextField(
                        value = title,
                        onValueChange = updateTitle,
                        modifier = Modifier.fillMaxWidth(),
                        isError = formcheck is FormState.Error,
                        supportingText = {
                            if (formcheck is FormState.Error)
                            {
                                Text(formcheck.message)
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        placeholder = {
                            Text("Enter your task title")
                        }
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = updateDescription,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 140.dp),
                        shape = RoundedCornerShape(18.dp),
                        placeholder = {
                            Text("Write a short note or details")
                        },
                        maxLines = 6
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Category",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(categories) { (status, label) ->

                            val isSelected = selectedCategory.equals(
                                status.name.lowercase(),
                                ignoreCase = true
                            )

                            FilterChip(
                                selected = isSelected,
                                onClick = { updateCategory(status) },
                                label = { Text(label) },
                                shape = RoundedCornerShape(50),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                                    labelColor = MaterialTheme.colorScheme.onSurface
                                )
                            )


                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.65f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Daily routine",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = "Turn this on for repeating daily habits.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Switch(
                            checked = isRoutine,
                            onCheckedChange = updateIsRoutine
                        )
                    }
                }
            }
        }

        Button(
            onClick = {updateTodoData(updateTodo.id)},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            )
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Update Task",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

