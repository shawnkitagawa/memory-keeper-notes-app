package com.example.memorykeeper.ui.screen

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.memorykeeper.R
import com.example.memorykeeper.ui.screen.data.Banner
import com.example.memorykeeper.ui.screen.data.banners
import com.example.memorykeeper.ui.theme.Dimens
import com.example.memorykeeper.ui.theme.MemoryKeeperTheme
import com.example.memorykeeper.ui.uistate.AuthCheck
import com.example.memorykeeper.ui.uistate.FilterStatus
import com.example.memorykeeper.ui.uistate.FormState
import com.example.memorykeeper.ui.uistate.Todo
import com.example.memorykeeper.ui.uistate.dashBoardUiState
import com.example.memorykeeper.ui.viewmodel.DashboardViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    uiState: dashBoardUiState,
    viewModel: DashboardViewModel,
    navToNext: (String) -> Unit,
    navToUpdate: (String, String) -> Unit
) {


    LaunchedEffect(Unit) {
        viewModel.loadDashboard()
        viewModel.resetTodoState()
        Log.d("dashboardScreen", "${uiState.authState}")
    }
    LaunchedEffect(uiState.formCheck) {
        if (uiState.formCheck == FormState.Success)
        {
            viewModel.loadDashboard()
        }
    }

    LaunchedEffect(uiState.authcheck) {
        if (uiState.authcheck == AuthCheck.LoggedOut)
        {
            navToNext("empty")
            viewModel.resetAuthCheckState()
        }

    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navToNext("plan") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task"
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            DashboardTopBar(
                userName = uiState.userName,
                onSignout = { viewModel.signOut() }
            )

            Spacer(modifier = Modifier.height(20.dp))

            HeroBannerSection(banners = banners)

            Spacer(modifier = Modifier.height(20.dp))

            FilterChipsSection(
                selected = uiState.filterStatus,
                onSelected = viewModel::updateFilter
            )

            Spacer(modifier = Modifier.height(20.dp))

            DashboardCardsRow(
                planList = uiState.toDo,
                updateComplete = viewModel::updatecompleted,
                filterStatus = uiState.filterStatus,
                navToUpdate = navToUpdate
            )

            Spacer(modifier = Modifier.height(20.dp))

            ProgressOverviewCard(
                countToDoTotal = uiState.toDo.size,
                countToDoCompleted = uiState.toDo.count { it.completed }
            )

            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}
@Composable
fun DashboardTopBar(
    userName: String,
    onSignout: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.icon_sample),
            contentDescription = "User Icon",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = stringResource(R.string.what_do_you_think_today),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Box {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Sign Out") },
                    onClick = {
                        expanded = false
                        onSignout()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeroBannerSection(
    banners: List<Banner>
) {
    val pagerState = rememberPagerState(pageCount = { banners.size })

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
        ) { page ->
            val banner = banners[page]

            HeroBannerCard(
                img = banner.img,
                textHeader = banner.header,
                textSubHeader = banner.subHeader
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(banners.size) { index ->
                val selected = index == pagerState.currentPage
                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(if (selected) 24.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (selected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
                        )
                )
            }
        }
    }
}

@Composable
fun HeroBannerCard(
    img: Int,
    textHeader: String,
    textSubHeader: String,
) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(img),
                contentDescription = "Hero Banner",
                contentScale = ContentScale.Crop,
                alignment = BiasAlignment(0f, -0.35f),
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.15f),
                                Color.Black.copy(alpha = 0.65f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            ) {
                Text(
                    text = textHeader,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = textSubHeader,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.88f)
                )
            }
        }
    }
}

@Composable
fun FilterChipsSection(
    selected: FilterStatus,
    onSelected: (FilterStatus) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 2.dp)
    ) {
        items(FilterStatus.values()) { filter ->
            val isSelected = selected == filter

            FilterChip(
                selected = isSelected,
                onClick = { onSelected(filter) },
                label = {
                    Text(
                        text = when (filter) {
                            FilterStatus.COMPLETED -> "Done"
                            else -> filter.name.lowercase()
                                .replaceFirstChar { it.uppercase() }
                        },
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                shape = RoundedCornerShape(50),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.surface,
                    labelColor = MaterialTheme.colorScheme.onSurface
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    borderColor = if (isSelected) {
                        Color.Transparent
                    } else {
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                    }
                )
            )
        }
    }
}
@Composable
fun DashboardCardsRow(
    planList: List<Todo>,
    updateComplete: (isCompleted: Boolean, id: String) -> Unit,
    filterStatus: FilterStatus,
    navToUpdate: (String, String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        DashboardTodoCard(
            modifier = Modifier.weight(1f),
            title = "Today's Plan",
            subtitle = "Focus on what matters",
            icon = Icons.Default.Edit,
            planList = planList,
            isRoutine = false,
            updateComplete = updateComplete,
            filterStatus = filterStatus,
            navToUpdate = navToUpdate
        )

        DashboardTodoCard(
            modifier = Modifier.weight(1f),
            title = "Daily Routine",
            subtitle = "Small habits every day",
            icon = Icons.Default.Refresh,
            planList = planList,
            isRoutine = true,
            updateComplete = updateComplete,
            filterStatus = filterStatus,
            navToUpdate = navToUpdate
        )
    }
}

@Composable
fun DashboardTodoCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    icon: ImageVector,
    planList: List<Todo>,
    isRoutine: Boolean,
    updateComplete: (isCompleted: Boolean, id: String) -> Unit,
    filterStatus: FilterStatus,
    navToUpdate: (String, String) -> Unit
) {
    Card(
        modifier = modifier.height(280.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            DashboardTodoList(
                modifier = Modifier.weight(1f),
                todoList = planList,
                isRoutine = isRoutine,
                updateComplete = updateComplete,
                filterStatus = filterStatus
            )

            Spacer(modifier = Modifier.height(10.dp))

            var showDialog by remember {mutableStateOf(false)}

            Text(
                text = "See all →",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable{
                    showDialog = true
                }
            )
            if (showDialog)
            {
                SeeAllDialog(
                    onDismiss = {showDialog = false},
                    todoList = planList,
                    isRoutine = isRoutine,
                    updateComplete = updateComplete,
                    filterStatus = filterStatus,
                    navToUpdate = navToUpdate

                )
            }
        }
    }
}
@Composable
fun SeeAllDialog(
    modifier: Modifier = Modifier,
    todoList: List<Todo>,
    isRoutine: Boolean,
    updateComplete: (isCompleted: Boolean, id: String) -> Unit,
    filterStatus: FilterStatus,
    onDismiss: () -> Unit,
    navToUpdate:(String, String) -> Unit
) {
    val category = when (filterStatus) {
        FilterStatus.WORK -> "work"
        FilterStatus.PERSONAL -> "personal"
        FilterStatus.HEALTH -> "health"
        FilterStatus.COMPLETED -> "completed"
        else -> "all"
    }

    val filteredTodos = todoList.filter { todo ->
        val categoryMatch =
            category == "all" ||
                    category == "completed" ||
                    todo.category == category

        val completionMatch =
            if (category == "completed") todo.completed
            else !todo.completed

        categoryMatch && completionMatch && todo.isRoutine == isRoutine
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (isRoutine) "All Routines" else "All Tasks"
            )
        },
        text = {
            if (filteredTodos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isRoutine) "No routines yet" else "No tasks yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = filteredTodos,
                        key = { it.id }
                    ) { todo ->
                        Dialogitem(
                            todo = todo,
                            updateComplete = updateComplete,
                            navToUpdate = navToUpdate
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun Dialogitem(
    todo: Todo,
    updateComplete: (isCompleted: Boolean, id: String) -> Unit,
    navToUpdate: (String, String) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { updateComplete(todo.completed, todo.id) },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = if (todo.completed) {
                        Icons.Default.CheckBox
                    } else {
                        Icons.Default.CheckBoxOutlineBlank
                    },
                    contentDescription = "Task Completion",
                    tint = if (todo.completed) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {

                // Title
                Text(
                    text = todo.plan,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (!todo.planDescription.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = todo.planDescription,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier= Modifier.weight(1f))
            IconButton(
                onClick = {navToUpdate("update", todo.id)}

            ) {
                Icon(Icons.Default.Update, contentDescription = "Update")
            }
        }
    }
}

@Composable
fun DashboardTodoList(
    modifier: Modifier = Modifier,
    todoList: List<Todo>,
    isRoutine: Boolean,
    updateComplete: (isCompleted: Boolean, id: String) -> Unit,
    filterStatus: FilterStatus,
) {
    val category = when (filterStatus) {
        FilterStatus.WORK -> "work"
        FilterStatus.PERSONAL -> "personal"
        FilterStatus.HEALTH -> "health"
        FilterStatus.COMPLETED -> "completed"
        else -> "all"
    }

    val filteredTodos = todoList.filter { todo ->
        val categoryMatch =
            category == "all" ||
                    category == "completed" ||
                    todo.category == category

        val completionMatch =
            if (category == "completed") todo.completed
            else !todo.completed

        categoryMatch && completionMatch && todo.isRoutine == isRoutine
    }

    if (filteredTodos.isEmpty()) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isRoutine) "No routines yet" else "No tasks yet",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = filteredTodos.take(4),
                key = { it.id }
            ) { todo ->
                DashboardTodoItem(
                    todo = todo,
                    updateComplete = updateComplete
                )
            }
        }
    }
}

@Composable
fun DashboardTodoItem(
    todo: Todo,
    updateComplete: (isCompleted: Boolean, id: String) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { updateComplete(todo.completed, todo.id) }
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { updateComplete(todo.completed, todo.id) },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = if (todo.completed) {
                        Icons.Default.CheckBox
                    } else {
                        Icons.Default.CheckBoxOutlineBlank
                    },
                    contentDescription = "Task Completion",
                    tint = if (todo.completed) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = todo.plan,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun ProgressOverviewCard(
    countToDoTotal: Int,
    countToDoCompleted: Int
) {
    val progress = if (countToDoTotal > 0) {
        countToDoCompleted.toFloat() / countToDoTotal.toFloat()
    } else {
        0f
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 700),
        label = "dashboard_progress"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Today's Progress",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "$countToDoCompleted of $countToDoTotal tasks completed",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.12f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = when {
                    countToDoTotal == 0 -> "Start by adding your first task."
                    animatedProgress >= 1f -> "Amazing. You cleared everything today."
                    animatedProgress >= 0.6f -> "Great momentum. Keep going."
                    animatedProgress > 0f -> "Nice start. Build the streak."
                    else -> "A fresh day to make progress."
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Preview
@Composable
fun DashboardPreview()
{
    MemoryKeeperTheme() {

    }
}