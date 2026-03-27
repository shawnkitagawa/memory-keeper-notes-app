package com.example.memorykeeper.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.memorykeeper.ui.screen.authentication.AuthScreen
import com.example.memorykeeper.ui.screen.authentication.AuthScreenSignUp
import com.example.memorykeeper.ui.uistate.AuthUiState
import com.example.memorykeeper.ui.viewmodel.AuthViewModel
import com.example.memorykeeper.ui.screen.DashboardScreen
import com.example.memorykeeper.ui.screen.EmptyScreen
import com.example.memorykeeper.ui.screen.TodoField
import com.example.memorykeeper.ui.screen.UpdateTodoField
import com.example.memorykeeper.ui.uistate.dashBoardUiState
import com.example.memorykeeper.ui.viewmodel.DashboardViewModel


@Composable
fun MemoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = viewModel(factory = DashboardViewModel.Factory),
) {
    NavHost(
        navController = navController, startDestination = "empty", modifier = modifier
    )
    {
        composable(route = "empty")
        {
            EmptyScreen(navToNext = {route -> navController.navigate(route)})
        }
        composable(route = "auth")
        {
            val viewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
            val uiState by viewModel.uiState.collectAsState()

            LaunchedEffect(uiState.authState) {
                if (uiState.authState is AuthUiState.Success)
                {
                    navController.navigate("dashboard")
                    {
                        popUpTo("auth"){inclusive = true }
                    }

                }
            }
            AuthScreen(viewModel = viewModel, uiState = uiState, navToSignUp = {navController.navigate("signup")})
        }

        composable(route = "signup")
        {
            val viewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
            val uiState by viewModel.uiState.collectAsState()

            LaunchedEffect(uiState.authState) {
                if (uiState.authState is AuthUiState.Success)
                {
                    navController.navigate("dashboard")
                    {
                        popUpTo("auth"){inclusive = true }
                    }

                }
            }
            AuthScreenSignUp(viewModel = viewModel, uiState = uiState)
        }
        composable(route = "dashboard")
        {
            val uiState by viewModel.uiState.collectAsState()
            DashboardScreen(uiState = uiState, viewModel = viewModel,navToNext = {route -> navController.navigate(route)}
            ,navToUpdate ={route, id -> navController.navigate("${route}/${id}")} )
        }
        composable(route = "plan")
        {
            val uiState by viewModel.uiState.collectAsState()
            Log.d("route plan", "${uiState.toDo}")
            TodoField(
                title = uiState.toDoInput.title,
                description = uiState.toDoInput.content ?: "",
                isRoutine = uiState.toDoInput.isRoutine,
                selectedCategory = uiState.toDoInput.filterStatus.name, // or just pass filterStatus (see below)

                updateTitle = viewModel::updateTitle,
                updateDescription = viewModel::updateDescription,
                updateIsRoutine = viewModel::updateIsRoutine,
                updateCategory = viewModel::updateCategory,
                addToDo = viewModel::addToDo,
                setAddTodo = viewModel::setAddTodo,
                navBack = {navController.popBackStack()},
                formcheck = uiState.formCheck,
                resetFormState = viewModel::resetFormState
            )
        }
        composable(route = "update/{id}")
        {
            backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val uiState by viewModel.uiState.collectAsState()

            val updateTodo = uiState.toDo.find{ todo ->
                todo.id == id
            }
            if (updateTodo != null)
            {
                UpdateTodoField(
                    title = uiState.toDoInput.title,
                    description = uiState.toDoInput.content ?: "",
                    isRoutine = uiState.toDoInput.isRoutine,
                    selectedCategory = uiState.toDoInput.filterStatus.name, // or just pass filterStatus (see below)
                    updateTitle = viewModel::updateTitle,
                    updateDescription = viewModel::updateDescription,
                    updateIsRoutine = viewModel::updateIsRoutine,
                    updateCategory = viewModel::updateCategory,
                    updateTodo = updateTodo,
                    updateTodoData = viewModel::updateTodoData,
                    setUpdateToDo = viewModel::setUpdateTodo,
                    navBack = {navController.navigate("dashboard")},
                    formcheck = uiState.formCheck,
                    resetFormState = viewModel::resetFormState
                )

            }
        }
    }
}